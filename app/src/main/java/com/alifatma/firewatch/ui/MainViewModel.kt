package com.alifatma.firewatch.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alifatma.firewatch.data.Result.Error
import com.alifatma.firewatch.data.Result.Loading
import com.alifatma.firewatch.data.Result.Success
import com.alifatma.firewatch.data.extractCenter
import com.alifatma.firewatch.data.extractPolygons
import com.alifatma.firewatch.repository.IncidentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class MainViewModel
@Inject constructor(
    private val incidentRepository: IncidentRepository
) : ViewModel() {

    private val TAG = "MainViewModel"

    private val _uiState = MutableStateFlow<RfsUiState>(RfsUiState.Loading)
    val uiState: StateFlow<RfsUiState> = _uiState

    init {
        load()
    }

    fun load() {

        viewModelScope.launch {
            when (val result = incidentRepository.getMajorIncidents()) {
                is Success -> {
                    val incidents = result.data.features
                    _uiState.value = RfsUiState.Success(incidents)
                    incidents.forEach { incident ->
                        val geometry = incident.geometry
                        val center = geometry?.extractCenter()
                        val polygons = geometry?.extractPolygons()
                        Log.d(
                            TAG,
                            "Incident: ${incident.properties.guid.substringAfterLast("/")}, Center: $center Polygons: ${polygons?.size ?: 0}"
                        )
                        polygons.let { polygon ->
                            polygon?.forEach {
                                Log.d(TAG, "Polygon with ${it.size} points")
                            }

                        }
                    }
                }

                is Error -> {
                    _uiState.value = RfsUiState.Error(result.message)
                    Log.e(TAG, "Error fetching incidents: ${result.message}", result.exception)
                }

                Loading -> {
                    _uiState.value = RfsUiState.Loading
                }
            }
        }
    }
}