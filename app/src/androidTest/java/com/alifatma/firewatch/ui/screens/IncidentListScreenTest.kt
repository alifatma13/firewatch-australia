package com.alifatma.firewatch.ui.screens

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.alifatma.firewatch.ui.RfsUiState
import com.alifatma.firewatch.ui.model.FireIncidentUiModel
import com.alifatma.firewatch.ui.theme.FireWatchTheme
import com.alifatma.firewatch.ui.util.TestTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI tests for IncidentListScreen.
 * Screens are rendered directly with a fake uiState — no Hilt / ViewModel needed.
 */
@RunWith(AndroidJUnit4::class)
class IncidentListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Loading state

    @Test
    fun loading_state_shows_loading_indicator() {
        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Loading,
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.LOADING_INDICATOR)
            .assertIsDisplayed()
    }

    @Test
    fun loading_state_does_not_show_error_message() {
        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Loading,
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.ERROR_MESSAGE)
            .assertDoesNotExist()
    }

    @Test
    fun loading_state_does_not_show_incident_list() {
        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Loading,
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.INCIDENT_LIST)
            .assertDoesNotExist()
    }

    // Error state

    @Test
    fun error_state_shows_correct_error_text() {
        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Error("network error"),
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        // Tag-based lookup is more reliable than text matching
        composeTestRule
            .onNodeWithTag(TestTags.ERROR_MESSAGE_TEXT)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("network error", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun error_state_does_not_show_loading_indicator() {
        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Error("network error"),
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.LOADING_INDICATOR)
            .assertDoesNotExist()
    }

    @Test
    fun error_state_does_not_show_incident_list() {
        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Error("network error"),
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.INCIDENT_LIST)
            .assertDoesNotExist()
    }

    // Success state

    @Test
    fun success_with_items_shows_incident_list_and_item_content() {
        val incidents = listOf(sampleIncident("Test Fire"))

        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.INCIDENT_LIST)
            .assertExists()

        composeTestRule
            .onNodeWithText("Test Fire")
            .assertExists()
    }

    @Test
    fun success_with_items_does_not_show_loading_or_error() {
        val incidents = listOf(sampleIncident("Test Fire"))

        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.LOADING_INDICATOR)
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithTag(TestTags.ERROR_MESSAGE)
            .assertDoesNotExist()
    }

    @Test
    fun success_with_multiple_items_all_cards_are_displayed() {
        val incidents = listOf(
            sampleIncident("Alpha Fire"),
            sampleIncident("Beta Fire"),
            sampleIncident("Gamma Fire"),
        )

        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.INCIDENT_LIST)
            .assertExists()

        composeTestRule.onNodeWithText("Alpha Fire").assertExists()
        composeTestRule.onNodeWithText("Beta Fire").assertExists()
        composeTestRule.onNodeWithText("Gamma Fire").assertExists()
    }

    @Test
    fun success_with_items_card_shows_alert_level_badge() {
        val incidents = listOf(sampleIncident("Alert Level Fire"))

        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        // Alert level is rendered uppercase in the card
        composeTestRule
            .onNodeWithText("ADVICE", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun success_with_items_card_shows_status_text() {
        val incidents = listOf(sampleIncident("Status Fire"))

        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        composeTestRule
            .onNodeWithText("Being controlled", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun success_with_items_card_shows_responsible_agency() {
        val incidents = listOf(sampleIncident("Agency Fire"))

        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        composeTestRule
            .onNodeWithText("RFS", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun success_with_items_action_icons_have_accessible_content_descriptions_and_click_actions() {
        val incidents = listOf(sampleIncident("Accessible Fire"))

        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(stringRes(com.alifatma.firewatch.R.string.incident_share_content_description))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .assertIsEnabled()

        composeTestRule
            .onNodeWithContentDescription(stringRes(com.alifatma.firewatch.R.string.incident_bookmark_content_description))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .assertIsEnabled()

        composeTestRule
            .onNodeWithTag(TestTags.INCIDENT_EXPAND_BUTTON)
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .assertIsEnabled()

        composeTestRule
            .onNodeWithContentDescription(stringRes(com.alifatma.firewatch.R.string.incident_expand))
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun success_with_items_card_show_view_map_button() {
        val incidents = listOf(
            sampleIncident("Fire Alpha"),
            sampleIncident("Fire Beta"), sampleIncident("Fire Gamma")
        )

        composeTestRule.setContent {
            FireWatchTheme() {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = {},
                    modifier = Modifier
                )
            }
        }

        composeTestRule
            .onAllNodesWithText("VIEW MAP", useUnmergedTree = true)
            .onFirst()
            .assertExists()
    }


    @Test
    fun success_with_items_card_shows_updated_time() {
        val incidents = listOf(sampleIncident("Updated Fire"))

        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        composeTestRule
            .onNodeWithText("19 May 2026 10:15", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun success_with_items_does_not_show_empty_state() {
        val incidents = listOf(sampleIncident("Non Empty Fire"))

        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.EMPTY_STATE_TEXT)
            .assertDoesNotExist()
    }

    @Test
    fun success_with_items_onIncidentClick_invoked_when_view_map_clicked() {
        var clickedId: String? = null
        val incidents = listOf(sampleIncident("Click Fire"))

        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = { id -> clickedId = id },
                    modifier = Modifier,
                )
            }
        }

        composeTestRule
            .onAllNodesWithText("VIEW MAP", useUnmergedTree = true)
            .onFirst()
            .performClick()

        assert(clickedId == "inc-1") {
            "Expected onIncidentClick to be called with 'inc-1' but got '$clickedId'"
        }
    }

    @Test
    fun success_empty_shows_empty_incident_component() {
        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Success(emptyList()),
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.EMPTY_STATE_TEXT)
            .assertIsDisplayed()


        composeTestRule
            .onNodeWithTag(TestTags.INCIDENT_LIST)
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithTag(TestTags.LOADING_INDICATOR)
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithTag(TestTags.ERROR_MESSAGE)
            .assertDoesNotExist()
    }

    // Expand Collapse Test

    @Test
    fun incident_cards_collapsed_by_default_extra_fields_hidden() {
        val incidents = listOf(sampleIncident("Collapsed Fire"))
        composeTestRule.setContent {
            FireWatchTheme() {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = {},
                    modifier = Modifier
                )
            }
        }
        composeTestRule.onNodeWithText("Council Area").assertDoesNotExist()
        //composeTestRule.onNodeWithText("City of Sydney").assertDoesNotExist()
    }

    @Test
    fun incident_card_expanded_on_chevron_click_extra_fields_displayed() {

        val incidents = listOf(sampleIncident("Expanded Fire"))

        composeTestRule.setContent {

            FireWatchTheme() {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = {},
                    modifier = Modifier
                )
            }
        }

        composeTestRule.onNodeWithTag(TestTags.INCIDENT_EXPAND_BUTTON, useUnmergedTree = true)
            .assertExists()
            .performClick()

        composeTestRule.onNodeWithText("Council Area ● City of Sydney").assertExists()

    }

    @Test
    fun incident_card_expand_state_is_isolated_between_cards() {
        val incidents = listOf(
            sampleIncident("Alpha Fire", id = "inc-1"),
            sampleSecondaryIncident(),
        )

        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        composeTestRule
            .onAllNodesWithContentDescription(
                stringRes(com.alifatma.firewatch.R.string.incident_expand),
                useUnmergedTree = true
            )
            .onFirst()
            .performClick()

        composeTestRule.onNodeWithText("Council Area ● City of Sydney").assertExists()
        composeTestRule.onNodeWithText("IMPACT SIZE", ignoreCase = true).assertExists()
        composeTestRule.onNodeWithText("1 ha", ignoreCase = true).assertExists()

        composeTestRule.onNodeWithText("Council Area ● Newcastle").assertDoesNotExist()
        composeTestRule.onNodeWithText("IMPACT SIZE", ignoreCase = true).assertExists()
        composeTestRule.onNodeWithText("2 ha", ignoreCase = true).assertDoesNotExist()
    }

    @Test
    fun check_visibility_of_fire_size_on_expansion_and_collapse_of_incident_card() {

        val incident = listOf(sampleIncident("New Fire Incident"))

        composeTestRule.setContent {
            FireWatchTheme() {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incident),
                    onIncidentClick = {},
                    modifier = Modifier
                )
            }
        }

        composeTestRule.onNodeWithText("IMPACT SIZE", ignoreCase = true).assertDoesNotExist()
        composeTestRule.onNodeWithText("1 ha", ignoreCase = true).assertDoesNotExist()

        composeTestRule.onNodeWithTag(TestTags.INCIDENT_EXPAND_BUTTON, useUnmergedTree = true)
            .assertExists()
            .performClick()

        composeTestRule.onNodeWithText("IMPACT SIZE", ignoreCase = true).assertExists()
        composeTestRule.onNodeWithText("1 ha", ignoreCase = true).assertExists()

        composeTestRule.onNodeWithTag(TestTags.INCIDENT_EXPAND_BUTTON, useUnmergedTree = true)
            .assertExists()
            .performClick()

        composeTestRule.onNodeWithText("IMPACT SIZE", ignoreCase = true).assertDoesNotExist()
        composeTestRule.onNodeWithText("1 ha", ignoreCase = true).assertDoesNotExist()
    }

    @Test
    fun unknown_value_fallback_is_used_for_null_fields_in_collapsed_state() {
        val incidents = listOf(
            FireIncidentUiModel(
                id = "unknown-1",
                title = "Unknown Collapsed",
                category = null,
                pubDate = null,
                location = null,
                status = null,
                responsibleAgency = null,
                councilArea = null,
                alertLevel = null,
                type = null,
                size = null,
                updated = null,
                center = null,
                polygons = emptyList(),
                extras = emptyMap(),
            )
        )

        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        // Collapsed state shows STATUS and AGENCY only, both should fall back to "-".
        composeTestRule
            .onAllNodesWithText("-", useUnmergedTree = true)
            .assertCountEquals(2)
    }

    @Test
    fun unknown_value_fallback_is_used_for_null_fields_in_expanded_state() {
        val incidents = listOf(
            FireIncidentUiModel(
                id = "unknown-2",
                title = "Unknown Expanded",
                category = null,
                pubDate = null,
                location = null,
                status = null,
                responsibleAgency = null,
                councilArea = null,
                alertLevel = null,
                type = null,
                size = null,
                updated = null,
                center = null,
                polygons = emptyList(),
                extras = emptyMap(),
            )
        )

        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = {},
                    modifier = Modifier,
                )
            }
        }

        composeTestRule.onNodeWithTag(TestTags.INCIDENT_EXPAND_BUTTON, useUnmergedTree = true)
            .assertExists()
            .performClick()

        // Expanded state renders 6 fallback values (status, category, size, agency, published, type).
        composeTestRule
            .onAllNodesWithText("-", useUnmergedTree = true)
            .assertCountEquals(6)
    }


    @Test
    fun after_scrolling_down_three_items_the_fab_icon_is_visible() {

        val incident = listOf(
            sampleIncident("New Fire Incident", "inc-1"),
            sampleIncident("Other Fire Incident", "inc-2"),
            sampleIncident("Another New Fire Incident", "inc-3"),
            sampleIncident("Bush Fire Incident", "inc-4"),
            sampleIncident("Planned Fire Incident", "inc-5")
        )

        composeTestRule.setContent {
            FireWatchTheme() {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incident),
                    onIncidentClick = {},
                    modifier = Modifier
                )
            }
        }

        composeTestRule.onNodeWithTag(TestTags.SCROLL_TO_TOP_FAB).assertDoesNotExist()
        composeTestRule.onNodeWithTag(TestTags.INCIDENT_LIST).performScrollToIndex(3)
        composeTestRule.onNodeWithTag(TestTags.SCROLL_TO_TOP_FAB).assertExists()

        composeTestRule
            .onNodeWithContentDescription(stringRes(com.alifatma.firewatch.R.string.scroll_to_top_content_description))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .assertIsEnabled()
    }


    @Test
    fun perform_click_on_fab_icon_and_it_scrolls_to_top() {
        val incident = listOf(
            sampleIncident("New Fire Incident", "inc-1"),
            sampleIncident("Other Fire Incident", "inc-2"),
            sampleIncident("Another New Fire Incident", "inc-3"),
            sampleIncident("Bush Fire Incident", "inc-4"),
            sampleIncident("Planned Fire Incident", "inc-5")
        )

        composeTestRule.setContent {
            FireWatchTheme() {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incident),
                    onIncidentClick = {},
                    modifier = Modifier
                )
            }
        }

        composeTestRule.onNodeWithTag(TestTags.INCIDENT_LIST).performScrollToIndex(3)
        composeTestRule.onNodeWithText("Bush Fire Incident").assertIsDisplayed()
        composeTestRule.onNodeWithText("New Fire Incident").assertDoesNotExist()

        composeTestRule.onNodeWithTag(TestTags.SCROLL_TO_TOP_FAB).assertExists()
        composeTestRule.onNodeWithTag(TestTags.SCROLL_TO_TOP_FAB).performClick()

        // Verify we returned to list position 0 by checking the first title is visible again.
        composeTestRule.onNodeWithText("New Fire Incident").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.SCROLL_TO_TOP_FAB).assertDoesNotExist()


    }




    @Test
    fun per_item_click_each_view_map_button_sends_correct_incident_id() {
        val clickedIds = mutableListOf<String>()

        val incidents = listOf(
            sampleIncident("Alpha Fire",  id = "inc-1"),
            sampleIncident("Beta Fire",   id = "inc-2"),
            sampleIncident("Gamma Fire",  id = "inc-3"),
        )

        composeTestRule.setContent {
            FireWatchTheme {
                IncidentListScreen(
                    uiState = RfsUiState.Success(incidents),
                    onIncidentClick = { id -> clickedIds.add(id) },
                    modifier = Modifier,
                )
            }
        }

        // Click VIEW MAP on each card in order (nodes are returned top-to-bottom)
        val viewMapNodes = composeTestRule
            .onAllNodesWithText("VIEW MAP", useUnmergedTree = true)

        viewMapNodes[0].performClick()
        assert(clickedIds.last() == "inc-1") {
            "Card 1: expected 'inc-1' but got '${clickedIds.lastOrNull()}'"
        }


        viewMapNodes[2].performClick()
        assert(clickedIds.last() == "inc-3") {
            "Card 3: expected 'inc-3' but got '${clickedIds.lastOrNull()}'"
        }

        assert(clickedIds.size == 2) {
            "Expected exactly 2 clicks to have been registered, got ${clickedIds.size}"
        }
    }

    private fun sampleIncident(title: String = "Small Fire", id: String = "inc-1"): FireIncidentUiModel =
        FireIncidentUiModel(
            id = id,
            title = title,
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
            center = null,
            polygons = emptyList(),
            extras = emptyMap(),
        )

    private fun sampleSecondaryIncident(): FireIncidentUiModel =
        FireIncidentUiModel(
            id = "inc-2",
            title = "Beta Fire",
            category = "Alert",
            pubDate = "19/05/2026 11:00:00 AM",
            location = "Newcastle",
            status = "Monitoring",
            responsibleAgency = "FRNSW",
            councilArea = "Newcastle",
            alertLevel = "Watch and Act",
            type = "Grass Fire",
            size = "2 ha",
            updated = "19 May 2026 11:15",
            center = null,
            polygons = emptyList(),
            extras = emptyMap(),
        )

    private fun stringRes(resId: Int): String =
        InstrumentationRegistry.getInstrumentation().targetContext.getString(resId)
}
