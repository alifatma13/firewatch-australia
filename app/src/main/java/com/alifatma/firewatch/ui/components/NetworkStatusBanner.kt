package com.alifatma.firewatch.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alifatma.firewatch.R
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun NetworkStatusBanner(
    isOffline: Boolean,
    lastSyncTime: Long?,
    modifier: Modifier = Modifier
) {
    if (isOffline) {
        val formattedTime = remember(lastSyncTime) {
            if (lastSyncTime != null) {
                val dateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(lastSyncTime),
                    ZoneId.systemDefault()
                )
                dateTime.format(DateTimeFormatter.ofPattern("MMM dd, HH:mm"))
            } else {
                "Never"
            }
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.errorContainer)
                .padding(vertical = 4.dp, horizontal = 16.dp)
                .testTag("NetworkStatusBanner"),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = String.format(
                    stringResource(id = R.string.incident_offline_message),
                    formattedTime
                ),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}
