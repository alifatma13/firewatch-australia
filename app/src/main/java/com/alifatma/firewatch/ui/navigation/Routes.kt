package com.alifatma.firewatch.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.ImageVector


/**
 * Centralized navigation route definition.
 */
object Routes {

    const val LIST = "list"
    const val MAP = "map"
    const val MAP_FOCUSED = "map/{incidentId}"

    const val INCIDENT_ID_ARG = "incidentId"

    // Helper function to generate focused map route with incident ID.
    fun mapFocusedRoute(incidentId: String) : String =
        "map/$incidentId"

    // Define bottom navigation items with their labels and routes.
    val BottomNavItems = listOf(
        BottomNavItem(label = "Incidents", route = LIST, icon = Icons.AutoMirrored.Filled.List),
        BottomNavItem(label = "Map", route = MAP, icon = Icons.Filled.Map)
    )

    data class BottomNavItem(
        val label: String,
        val route : String,
        val icon : ImageVector
    )
}