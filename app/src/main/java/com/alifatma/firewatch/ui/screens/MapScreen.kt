package com.alifatma.firewatch.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.alifatma.firewatch.ui.RfsUiState
import com.alifatma.firewatch.ui.model.FireIncidentUiModel
import com.alifatma.firewatch.ui.theme.LocalCardContainerColor
import com.alifatma.firewatch.ui.util.alertLevelToHue
import com.alifatma.firewatch.ui.util.getColorForAlertLevel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState


@Composable
fun MapScreen(
    uiState: RfsUiState,
    focusedIncidentId: String?,
    modifier: Modifier
) {
    // Coordinates for the center of Australia
    val australiaLatLng = LatLng(-25.2744, 133.7751)

    val incidents = (uiState as? RfsUiState.Success)?.incidents ?: emptyList()

    val focusedIncident = incidents.find {
        it.id == focusedIncidentId
    }

    var mapLoaded by remember { mutableStateOf(false) }

    val target = focusedIncident?.center?.let {
        LatLng(it.lat, it.lng)
    } ?: australiaLatLng

    val zoom = if (focusedIncident != null) 12f else 4f

    // Initialize camera position with a zoom level of 4f if no focused incident else 12f
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(australiaLatLng, zoom)
    }

    LaunchedEffect(mapLoaded, focusedIncident?.center) {
        if (mapLoaded) {
            focusedIncident?.center?.let {
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngZoom(target, 12f)
                )
            }
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        AustraliaMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = { mapLoaded = true },
            incidents
        )
    }
}

// Google Map view
@Composable
fun AustraliaMap(
    modifier: Modifier,
    cameraPositionState: CameraPositionState,
    onMapLoaded: () -> Unit,
    incidents: List<FireIncidentUiModel>
) {

    val infiniteTransition = rememberInfiniteTransition(label = "fire")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )
    var selectedIncident by remember { mutableStateOf<FireIncidentUiModel?>(null) }

    var screenWidth by remember { mutableIntStateOf(0) }
    var screenHeight by remember { mutableIntStateOf(0) }
    var cardWidth by remember { mutableIntStateOf(0) }
    var cardHeight by remember { mutableIntStateOf(0) }





    Box(modifier = modifier.onGloballyPositioned {
        screenWidth = it.size.width
        screenHeight = it.size.height
    }) {

        GoogleMap(
            modifier = modifier,
            cameraPositionState = cameraPositionState,
            onMapLoaded = onMapLoaded,
            onMapClick = { selectedIncident = null }
        ) {
            incidents.forEach { incident ->
                incident.center?.let { center ->

                    val icon = remember(incident.alertLevel) {
                        BitmapDescriptorFactory.defaultMarker(alertLevelToHue(incident.alertLevel))
                    }

                    Marker(
                        icon =  icon,
                        state = rememberUpdatedMarkerState(
                            position = LatLng(
                                center.lat,
                                center.lng
                            )
                        ),
                        onClick = {
                            selectedIncident = incident
                            true
                        }
                    )
                }
            }

            incidents.forEach { incident ->
                incident.polygons?.forEach { ring ->
                    Polygon(
                        points = ring.map { LatLng(it.lat, it.lng) },
                        fillColor = Color(1f, 0.27f, 0f, alpha),
                        strokeColor = Color(0xFFFF4500),
                        strokeWidth = 6f,
                        strokePattern = listOf(Dot(), Gap(15f))
                    )

                }

            }
        }

        selectedIncident?.let { incident ->
            incident.center?.let { center ->

                val point = cameraPositionState.projection
                    ?.toScreenLocation(LatLng(center.lat, center.lng))

                point?.let { markerPoint ->

                    val yPos = if (markerPoint.y - cardHeight - 80 >= 0) {
                        markerPoint.y - cardHeight - 80
                    } else {
                        markerPoint.y + 80
                    }

                    val xPos = (markerPoint.x - cardWidth / 2)
                        .coerceIn(0, (screenWidth - cardWidth).coerceAtLeast(0))


                    Card(
                        modifier = Modifier
                            .absoluteOffset { IntOffset(xPos, yPos) }
                            .onGloballyPositioned {
                                cardWidth = it.size.width
                                cardHeight = it.size.height
                            }
                            .width(250.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = LocalCardContainerColor.current
                        )

                    ) {

                        Column(modifier = Modifier.padding(0.dp)) {

                            val alertColor = getColorForAlertLevel(incident.alertLevel)

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(alertColor)
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = incident.alertLevel?.uppercase() ?: "UNKNOWN",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White
                                )
                            }

                            Column(modifier = Modifier.padding(12.dp)) {

                                Text(
                                    text = incident.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                                Spacer(modifier = Modifier.height(8.dp))

                                // data rows — label in OnSurfaceVariant, value in OnSurface
                                InfoRow(label = "STATUS", value = incident.status)
                                InfoRow(label = "TYPE", value = incident.type)
                                InfoRow(label = "AGENCY", value = incident.responsibleAgency)
                            }
                        }

                    }

                }
            }

        }

    }
}


@Composable
private fun InfoRow(label: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
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

