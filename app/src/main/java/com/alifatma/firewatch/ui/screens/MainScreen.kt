package com.alifatma.firewatch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.alifatma.firewatch.ui.MainViewModel
import com.alifatma.firewatch.ui.components.FireWatchTopAppBar
import com.alifatma.firewatch.ui.navigation.BottomNavBar
import com.alifatma.firewatch.ui.navigation.FireWatchNavGraph
import com.alifatma.firewatch.ui.theme.OnPrimaryContainer
import com.alifatma.firewatch.ui.theme.Surface

@Composable
fun MainScreen(
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.background(Surface),
        topBar = {
            FireWatchTopAppBar()
        },
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        FireWatchNavGraph(
            navHostController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}