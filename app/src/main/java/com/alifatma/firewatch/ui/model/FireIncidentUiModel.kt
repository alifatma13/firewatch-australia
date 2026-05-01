package com.alifatma.firewatch.ui.model

import androidx.compose.ui.graphics.Color
import com.alifatma.firewatch.data.Coordinates

/**
 * UI model for displaying a fire incident in the list.
 * Combines RfsProperties, IncidentDetails, and extracted incidentId.
 */
data class FireIncidentUiModel(
    val id: String?,
    val title: String,
    val category: String?,
    val pubDate: String?,
    val location: String?,
    val status: String?,
    val responsibleAgency: String?,
    val councilArea: String?,
    val alertLevel: String?,
    val type: String?,
    val size: String?,
    val updated: String?,
    val center: Coordinates? = null,
    val polygons: List<List<Coordinates>>? = emptyList(),
    val extras: Map<String, String> = emptyMap()
)
