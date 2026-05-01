package com.alifatma.firewatch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alifatma.firewatch.ui.MainViewModel
import com.alifatma.firewatch.ui.RfsUiState
import com.alifatma.firewatch.ui.components.ErrorMessageComponent
import com.alifatma.firewatch.ui.components.FireLoadingIndicator
import com.alifatma.firewatch.ui.components.HeaderComponent
import com.alifatma.firewatch.ui.components.IncidentItemComponent
import com.alifatma.firewatch.ui.theme.FireWatchTypography
import com.alifatma.firewatch.ui.theme.OnPrimaryContainer

@Composable
fun IncidentListScreen(
    uiState: RfsUiState,
    onIncidentClick: (String) -> Unit,
    modifier: Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        HeaderComponent(modifier = Modifier.padding(bottom = 8.dp))

        when (uiState) {
            is RfsUiState.Loading -> FireLoadingIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            is RfsUiState.Error -> ErrorMessageComponent(
                message = uiState.message,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            is RfsUiState.Success -> {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start) {
                    items(uiState.incidents) { incident ->
                        IncidentItemComponent(
                            incident = incident,
                            onViewMap = { incident.id?.let { onIncidentClick(it) } }
                        )
                    }
                }

            }

        }
    }
}