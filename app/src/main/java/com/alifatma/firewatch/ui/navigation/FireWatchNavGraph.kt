package com.alifatma.firewatch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alifatma.firewatch.ui.MainViewModel
import com.alifatma.firewatch.ui.screens.IncidentListScreen
import com.alifatma.firewatch.ui.screens.MapScreen
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FireWatchNavGraph(
    navHostController: NavHostController,
    modifier: Modifier
) {

    NavHost(
        navController = navHostController,
        startDestination = Routes.LIST,
        modifier = modifier
    ) {
        composable(Routes.LIST) {
            val viewModel: MainViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            IncidentListScreen(
                uiState = uiState,
                onIncidentClick = { incidentId ->
                    navHostController.navigate(Routes.mapFocusedRoute(incidentId))
                },
                modifier = Modifier,
            )
        }

        composable(Routes.MAP) {
            MapScreen(focusedIncidentId = null, modifier = Modifier)
        }

        composable(
            Routes.MAP_FOCUSED,
            arguments = listOf(navArgument(Routes.INCIDENT_ID_ARG) { type = NavType.StringType })
        ) { backStackEntry ->
            val incidentId = backStackEntry.arguments?.getString(Routes.INCIDENT_ID_ARG)
            MapScreen(focusedIncidentId = incidentId, modifier = Modifier)
        }

    }

}