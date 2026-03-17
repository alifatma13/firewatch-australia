package com.alifatma.firewatch.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alifatma.firewatch.data.RfsProperties
import com.alifatma.firewatch.data.extractCenter
import com.alifatma.firewatch.data.extractPolygons
import com.alifatma.firewatch.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val TAG = "MainViewModel"

    val incidents = MutableStateFlow<List<RfsProperties>>(emptyList())
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    fun load() {

        viewModelScope.launch {

            try {
                val result = RetrofitClient.api.getMajorIncidents()
                incidents.update { result.features.map { it.properties } }
                result.features.forEach {
                    val center = it.geometry?.extractCenter()
                    val polygons = it.geometry?.extractPolygons()
                    Log.d(TAG, "[${it.properties.category}] ${it.properties.title}")
                    Log.d(TAG, "  center=$center polygons=${polygons?.size}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "failed to fetch: ${e.message}")
                _error.update { e.message }
            }
        }
    }
}