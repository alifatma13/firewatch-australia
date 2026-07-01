package com.alifatma.firewatch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.alifatma.firewatch.ui.MainViewModel
import com.alifatma.firewatch.ui.components.FireWatchTopAppBar
import com.alifatma.firewatch.ui.components.NetworkStatusBanner
import com.alifatma.firewatch.ui.navigation.BottomNavBar
import com.alifatma.firewatch.ui.navigation.FireWatchNavGraph
import com.alifatma.firewatch.ui.theme.Surface

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val isOffline by viewModel.isOffline.collectAsStateWithLifecycle()
    val lastSyncTime by viewModel.lastSyncTime.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.background(Surface),
        topBar = {
            FireWatchTopAppBar()
        },
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            NetworkStatusBanner(isOffline = isOffline, lastSyncTime = lastSyncTime)
            FireWatchNavGraph(
                navHostController = navController,
                modifier = Modifier
            )
        }
    }
}