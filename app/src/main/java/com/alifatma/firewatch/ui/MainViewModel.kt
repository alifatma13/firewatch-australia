package com.alifatma.firewatch.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alifatma.firewatch.data.Result.Error
import com.alifatma.firewatch.data.Result.Loading
import com.alifatma.firewatch.data.Result.Success
import com.alifatma.firewatch.repository.IncidentRepository
import com.alifatma.firewatch.ui.model.FireIncidentUiModel
import com.alifatma.firewatch.ui.model.toUiModel
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

    private val _uiState = MutableStateFlow<RfsUiState>(RfsUiState.Loading)
    val uiState: StateFlow<RfsUiState> = _uiState
    var incidents: List<FireIncidentUiModel> = emptyList()

    // make this public and call from composable
    // with the launched effect
    fun load() {

        viewModelScope.launch {
            when (val result = incidentRepository.getMajorIncidents()) {
                is Success -> {
                    incidents = result.data.features.map {
                        it.toUiModel()
                    }
                    Log.i(TAG, "Successfully fetched ${incidents.size} incidents")
                    _uiState.update { RfsUiState.Success(incidents) }
                }

                is Error -> {
                    _uiState.update { RfsUiState.Error(result.message) }
                    Log.e(TAG, "Error fetching incidents: ${result.message}", result.exception)
                }

                Loading -> {
                    _uiState.update { RfsUiState.Loading }
                }
            }
        }
    }
}