package com.alifatma.firewatch.ui

import com.alifatma.firewatch.data.RfsFeature

sealed class RfsUiState{
    data class Success(val incidents: List<RfsFeature>): RfsUiState()
    data class Error(val message: String): RfsUiState()
    object Loading : RfsUiState()
}