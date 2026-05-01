package com.alifatma.firewatch.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alifatma.firewatch.R
import com.alifatma.firewatch.ui.model.FireIncidentUiModel
import com.alifatma.firewatch.ui.theme.FireWatchTypography
import com.alifatma.firewatch.ui.theme.PrimaryContainer
import com.alifatma.firewatch.ui.theme.SurfaceContainer
import com.alifatma.firewatch.ui.util.formatPublishedDate
import com.alifatma.firewatch.ui.util.getColorForAlertLevel
import com.alifatma.firewatch.ui.util.getColorForStatus
import com.alifatma.firewatch.ui.util.toCamelCase

/**
 * Lazy loaded incident item component
 * All relevant information is displayed on card
 */

@Composable
fun IncidentItemComponent(
    incident: FireIncidentUiModel,
    onViewMap: () -> Unit = {},
    onShare: () -> Unit = {},
    onBookmark: () -> Unit = {}
) {
    val unknownValue = stringResource(R.string.incident_unknown_value)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Alert Level and Updated Time
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                incident.alertLevel?.let {
                    Text(
                        text = it.uppercase(),
                        color = Color.White,
                        style = FireWatchTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .background(
                                getColorForAlertLevel(it), RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                incident.updated?.let {
                    Text(text = it, style = FireWatchTypography.labelSmall, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Title
            Text(
                text = incident.title.toCamelCase(),
                style = FireWatchTypography.titleLarge.copy(fontWeight = FontWeight.Black),
                modifier = Modifier.padding(bottom = 2.dp)
            )
            // Council/Location
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(
                        Icons.Outlined.LocationOn,
                        contentDescription = stringResource(R.string.incident_location_content_description),
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    incident.location?.let {
                        if (incident.councilArea != null)
                            Text(
                                text = it.toCamelCase(),
                                style = FireWatchTypography.bodySmall,
                                color = Color.Gray
                            )
                    }
                }
                incident.councilArea?.let {
                    Text(
                        text = stringResource(R.string.incident_council_area_format, it),
                        modifier = Modifier.padding(start = 20.dp),
                        style = FireWatchTypography.bodySmall,
                        color = Color.Gray
                    )
                }

            }
            Spacer(modifier = Modifier.height(12.dp))
            // Status and Category
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stringResource(R.string.incident_status_label),
                        style = FireWatchTypography.labelSmall,
                        color = Color.Gray
                    )
                    Text(
                        text = incident.status ?: unknownValue,
                        style = FireWatchTypography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = getColorForStatus(incident.status)
                        )
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stringResource(R.string.incident_category_label),
                        style = FireWatchTypography.labelSmall,
                        color = Color.Gray
                    )
                    Text(
                        text = incident.category ?: unknownValue,
                        style = FireWatchTypography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            // Details Row
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stringResource(R.string.incident_impact_size_label),
                        style = FireWatchTypography.labelSmall,
                        color = Color.Gray
                    )
                    Text(
                        text = incident.size ?: unknownValue,
                        style = FireWatchTypography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stringResource(R.string.incident_agency_label),
                        style = FireWatchTypography.labelSmall,
                        color = Color.Gray
                    )
                    Text(
                        text = incident.responsibleAgency ?: unknownValue,
                        style = FireWatchTypography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stringResource(R.string.incident_published_label),
                        style = FireWatchTypography.labelSmall,
                        color = Color.Gray
                    )
                    Text(
                        text = formatPublishedDate(incident.pubDate, unknownValue),
                        style = FireWatchTypography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stringResource(R.string.incident_type_label),
                        style = FireWatchTypography.labelSmall,
                        color = Color.Gray
                    )
                    Text(
                        text = incident.type ?: unknownValue,
                        style = FireWatchTypography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            // Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onViewMap,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        stringResource(R.string.incident_view_map),
                        style = FireWatchTypography.labelLarge
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = onShare) {
                    Icon(
                        Icons.Filled.Share,
                        contentDescription = stringResource(R.string.incident_share_content_description)
                    )
                }
                IconButton(onClick = onBookmark) {
                    Icon(
                        Icons.Filled.BookmarkBorder,
                        contentDescription = stringResource(R.string.incident_bookmark_content_description)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun IncidentItemComponentPreview() {
    val sampleIncident = FireIncidentUiModel(
        id = "1",
        title = "Large Bushfire in Sydney",
        category = "Bushfire",
        pubDate = "2024-06-01 14:00",
        location = "Sydney, NSW",
        status = "Active",
        responsibleAgency = "NSW Rural Fire Service",
        councilArea = "Sydney City Council",
        alertLevel = "Severe",
        type = "Bushfire",
        size = "500 hectares",
        updated = "10 minutes ago"
    )
    IncidentItemComponent(incident = sampleIncident)
}