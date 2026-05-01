package com.alifatma.firewatch.ui.components


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.alifatma.firewatch.ui.theme.FireWatchTypography
import com.alifatma.firewatch.ui.theme.Primary

// Custom Top App Bar with title, search and settings
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FireWatchTopAppBar(
    onSearchClick: () -> Unit = {},
    onFilterClick: () -> Unit = {}
) {
    TopAppBar(
        title = { Title() },
        navigationIcon = {
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        },
        actions = {
            IconButton(onClick = onFilterClick) {
                Icon(Icons.Filled.Tune, contentDescription = "Filter/Settings")
            }
        },
        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Primary,
            navigationIconContentColor = Color.DarkGray,
            actionIconContentColor = Color.DarkGray
        )
    )
}

@Composable
fun Title() {
    Text(
        text = "SENTINEL",
        style = FireWatchTypography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        ),
        color = Primary
    )
}

@Preview
@Composable
fun PreviewFireWatchTopAppBar() {
    FireWatchTopAppBar()
}
