package com.alifatma.firewatch.ui.navigation


import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alifatma.firewatch.ui.theme.FireWatchTypography
import androidx.compose.ui.platform.testTag


@Composable
fun BottomNavBar(
    navController: NavController
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry.value?.destination

    BottomNavigation(
        modifier = Modifier.navigationBarsPadding(),
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ) {
        Routes.BottomNavItems.forEach { item ->
            val isSelected = destination
                ?.hierarchy
                ?.any { navDestination ->
                    navDestination.route == item.route ||
                            (item.route == Routes.MAP && navDestination.route == Routes.MAP_FOCUSED)
                } == true

            BottomNavigationItem(
                modifier = Modifier.testTag("tab_${item.route}"),
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, style = FireWatchTypography.labelMedium) },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = false
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                }
            )
        }

    }


}