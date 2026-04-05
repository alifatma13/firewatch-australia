package com.alifatma.firewatch.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alifatma.firewatch.data.RfsProperties
import com.alifatma.firewatch.data.extractCenter
import com.alifatma.firewatch.data.extractPolygons
import com.alifatma.firewatch.network.RfsApiService
import com.alifatma.firewatch.repository.IncidentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class MainViewModel
@Inject constructor(
    private val incidentRepository: IncidentRepository
) : ViewModel() {

    private val TAG = "MainViewModel"

    val _incidents = MutableStateFlow<List<RfsProperties>>(emptyList())
    val incidents: StateFlow<List<RfsProperties>> = _incidents
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    fun load() {

        viewModelScope.launch {

            try {
                val result = incidentRepository.getMajorIncidents()
                result.onSuccess { featureCollection ->
                    _incidents.update { featureCollection.features.map { it.properties } }
                    featureCollection.features.forEach {
                        val center = it.geometry?.extractCenter()
                        val polygons = it.geometry?.extractPolygons()
                        Log.d(TAG, "[${it.properties.category}] ${it.properties.title}")
                        Log.d(TAG, "  center=$center polygons=${polygons?.size}")
            }
                }.onFailure { e ->
                    Log.e(TAG, "failed to fetch: ${e.message}")
                    _error.update { e.message }
                }
            } catch (e: Exception) {
                Log.e(TAG, "unexpected error: ${e.message}")
                _error.update { e.message }
            }

        }
    }

}