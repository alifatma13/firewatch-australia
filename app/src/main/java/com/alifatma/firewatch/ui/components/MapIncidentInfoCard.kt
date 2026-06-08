package com.alifatma.firewatch.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.alifatma.firewatch.ui.model.FireIncidentUiModel
import com.alifatma.firewatch.ui.theme.LocalCardContainerColor
import com.alifatma.firewatch.ui.util.TestTags
import com.alifatma.firewatch.ui.util.getColorForAlertLevel

@Composable
fun MapIncidentInfoCard(
    incident: FireIncidentUiModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(250.dp)
            .testTag(TestTags.MAP_INCIDENT_INFO_CARD),
        colors = CardDefaults.cardColors(
            containerColor = LocalCardContainerColor.current
        )
    ) {
        Column(modifier = Modifier.padding(0.dp)) {
            val alertColor = getColorForAlertLevel(incident.alertLevel)

            BoxHeader(
                alertLevel = incident.alertLevel?.uppercase() ?: "UNKNOWN",
                alertColor = alertColor
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = incident.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(modifier = Modifier.height(8.dp))

                MapInfoRow(label = "STATUS", value = incident.status)
                MapInfoRow(label = "TYPE", value = incident.type)
                MapInfoRow(label = "AGENCY", value = incident.responsibleAgency)
            }
        }
    }
}

@Composable
private fun BoxHeader(alertLevel: String, alertColor: Color) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(alertColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = alertLevel,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White
        )
    }
}

@Composable
private fun MapInfoRow(label: String, value: String?) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value ?: "-",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}