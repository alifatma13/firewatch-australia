package com.alifatma.firewatch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alifatma.firewatch.ui.MainViewModel
import com.alifatma.firewatch.ui.screens.IncidentListScreen
import com.alifatma.firewatch.ui.screens.MapScreen

@Composable
fun FireWatchNavGraph(
    navHostController: NavHostController,
    modifier: Modifier
) {

    val viewModel: MainViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle() // is life cycle aware


    NavHost(
        navController = navHostController,
        startDestination = Routes.LIST,
        modifier = modifier
    ) {
        composable(Routes.LIST) {
            LaunchedEffect(Unit) { // one off load of list items
                viewModel.load()
            }

            IncidentListScreen(
                uiState = uiState,
                onIncidentClick = { incidentId ->
                    navHostController.navigate(Routes.mapFocusedRoute(incidentId))
                },
                modifier = Modifier,
            )
        }

        composable(Routes.MAP) {
            MapScreen(uiState = uiState, focusedIncidentId = null, modifier = Modifier)
        }

        composable(
            Routes.MAP_FOCUSED,
            arguments = listOf(navArgument(Routes.INCIDENT_ID_ARG) { type = NavType.StringType })
        ) { backStackEntry ->
            val incidentId = backStackEntry.arguments?.getString(Routes.INCIDENT_ID_ARG)
            MapScreen(uiState = uiState, focusedIncidentId = incidentId, modifier = Modifier)
        }

    }

}