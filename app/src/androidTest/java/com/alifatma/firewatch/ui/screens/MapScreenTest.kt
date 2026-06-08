package com.alifatma.firewatch.ui.screens

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alifatma.firewatch.data.Coordinates
import com.alifatma.firewatch.ui.RfsUiState
import com.alifatma.firewatch.ui.model.FireIncidentUiModel
import com.alifatma.firewatch.ui.theme.FireWatchTheme
import com.alifatma.firewatch.ui.util.TestTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MapScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mapScreen_renders_map_container() {
        composeTestRule.setContent {
            FireWatchTheme {
                MapScreen(
                    uiState = RfsUiState.Success(listOf(sampleIncident("inc-1"))),
                    focusedIncidentId = null,
                    modifier = Modifier
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.MAP_CONTAINER, useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun map_no_selected_incident_card_by_default() {
        composeTestRule.setContent {
            FireWatchTheme {
                MapScreen(
                    uiState = RfsUiState.Success(listOf(sampleIncident("inc-1"))),
                    focusedIncidentId = null,
                    modifier = Modifier
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.MAP_INCIDENT_INFO_CARD, useUnmergedTree = true)
            .assertDoesNotExist()
    }

    @Test
    fun mapScreen_focusedIncidentId_renders_map_container() {
        composeTestRule.setContent {
            FireWatchTheme {
                MapScreen(
                    uiState = RfsUiState.Success(
                        listOf(
                            sampleIncident("inc-1"),
                            sampleIncident("inc-2")
                        )
                    ),
                    focusedIncidentId = "inc-2",
                    modifier = Modifier
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.MAP_CONTAINER, useUnmergedTree = true)
            .assertExists()
    }

    private fun sampleIncident(id: String): FireIncidentUiModel =
        FireIncidentUiModel(
            id = id,
            title = "Test Fire $id",
            category = "Advice",
            pubDate = "19/05/2026 10:00:00 AM",
            location = "Sydney",
            status = "Being controlled",
            responsibleAgency = "RFS",
            councilArea = "City of Sydney",
            alertLevel = "Advice",
            type = "Bush Fire",
            size = "1 ha",
            updated = "19 May 2026 10:15",
            center = Coordinates(-33.8688, 151.2093),
            polygons = emptyList(),
            extras = emptyMap(),
        )
}