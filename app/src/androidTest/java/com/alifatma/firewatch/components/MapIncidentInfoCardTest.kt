package com.alifatma.firewatch.components


import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.alifatma.firewatch.ui.components.MapIncidentInfoCard
import com.alifatma.firewatch.ui.model.FireIncidentUiModel
import com.alifatma.firewatch.ui.theme.FireWatchTheme
import com.alifatma.firewatch.ui.util.TestTags
import org.junit.Rule
import org.junit.Test

class MapIncidentInfoCardTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun card_renders_core_fields() {
        composeRule.setContent {
            FireWatchTheme {
                MapIncidentInfoCard(incident = sampleIncident())
            }
        }

        composeRule.onNodeWithTag(TestTags.MAP_INCIDENT_INFO_CARD).assertExists()
        composeRule.onNodeWithText("Test Fire").assertExists()
        composeRule.onNodeWithText("ADVICE").assertExists()
        composeRule.onNodeWithText("STATUS").assertExists()
        composeRule.onNodeWithText("Being controlled").assertExists()
        composeRule.onNodeWithText("TYPE").assertExists()
        composeRule.onNodeWithText("Bush Fire").assertExists()
        composeRule.onNodeWithText("AGENCY").assertExists()
        composeRule.onNodeWithText("RFS").assertExists()
    }

    @Test
    fun card_uses_fallback_for_null_values() {
        composeRule.setContent {
            FireWatchTheme {
                MapIncidentInfoCard(
                    incident = sampleIncident(
                        status = null,
                        type = null,
                        agency = null,
                        alertLevel = null
                    )
                )
            }
        }

        composeRule
            .onNodeWithText("UNKNOWN", useUnmergedTree = true)
            .assertExists()

        composeRule
            .onAllNodesWithText("-", useUnmergedTree = true)
            .assertCountEquals(3)
    }

    private fun sampleIncident(
        status: String? = "Being controlled",
        type: String? = "Bush Fire",
        agency: String? = "RFS",
        alertLevel: String? = "Advice"
    ): FireIncidentUiModel =
        FireIncidentUiModel(
            id = "inc-1",
            title = "Test Fire",
            category = "Advice",
            pubDate = "19/05/2026 10:00:00 AM",
            location = "Sydney",
            status = status,
            responsibleAgency = agency,
            councilArea = "City of Sydney",
            alertLevel = alertLevel,
            type = type,
            size = "1 ha",
            updated = "19 May 2026 10:15",
            center = null,
            polygons = emptyList(),
            extras = emptyMap(),
        )
}