package com.alifatma.firewatch.ui

import com.alifatma.firewatch.ui.model.FireIncidentUiModel

sealed class RfsUiState{
    data class Success(val incidents: List<FireIncidentUiModel>): RfsUiState()
    data class Error(val message: String): RfsUiState()
    object Loading : RfsUiState()
}