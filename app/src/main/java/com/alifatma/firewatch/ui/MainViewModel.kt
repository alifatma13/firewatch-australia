package com.alifatma.firewatch.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alifatma.firewatch.data.Result.Error
import com.alifatma.firewatch.data.Result.Loading
import com.alifatma.firewatch.data.Result.Success
import com.alifatma.firewatch.data.RfsProperties
import com.alifatma.firewatch.data.extractCenter
import com.alifatma.firewatch.data.extractPolygons
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

    private val _incidents = MutableStateFlow<List<RfsProperties>>(emptyList())
    val incidents: StateFlow<List<RfsProperties>> = _incidents
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun load() {

        viewModelScope.launch {
            val result = incidentRepository.getMajorIncidents()
            when (result) {
                is Loading -> {
                    Log.d(TAG, "loading incidents...")
                }

                is Success -> {
                    Log.d(TAG, "successfully fetched ${result.data.features.size} incidents")
                    _incidents.update {
                        result.data.features.map { it.properties }
                    }
                    result.data.features.forEach {
                        val center = it.geometry?.extractCenter()
                        val polygons = it.geometry?.extractPolygons()
                        Log.d(TAG, "[${it.properties.category}] ${it.properties.title}")
                        Log.d(TAG, "  center=$center polygons=${polygons?.size}")
                    }
                }

                is Error -> {
                    Log.e(TAG, "failed to fetch: ${result.message}")
                    _error.update { result.message }
                }
            }
        }
    }
}