package com.alifatma.firewatch.ui.screens


import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.alifatma.firewatch.MainActivity
import com.alifatma.firewatch.data.Result
import com.alifatma.firewatch.data.RfsFeatureCollection
import com.alifatma.firewatch.data.RfsFeaturesStub
import com.alifatma.firewatch.di.FakeRepositoryModule
import com.alifatma.firewatch.di.RepositoryModule
import com.alifatma.firewatch.ui.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * UI tests for IncidentListScreen.
 * Screens are rendered with Hilt / ViewModel .
 */
@HiltAndroidTest
@UninstallModules(RepositoryModule::class)
class IncidentListScreenIntegratedTest {

    private val fakeRepository get() = FakeRepositoryModule.fakeRepository

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        fakeRepository.reset()
    }

    @Test
    fun incidentListScreen_displaysIncidents_whenApiReturnsSuccess() {
        fakeRepository.emit(
            Result.Success(
                RfsFeatureCollection(
                    type = "FeatureCollection",
                    features = RfsFeaturesStub.pointIncidentStubForParsingProperties
                )
            )
        )

        composeRule.waitForIdle()
        composeRule.onNodeWithText("MAIN CREEK RD, MAIN CREEK", ignoreCase = true).assertExists()

    }

    @Test
    fun incidentListScreen_displayLoading_whenApiIsFetched(){

        fakeRepository.emit(
            Result.Loading
        )
        composeRule.onNodeWithTag(TestTags.LOADING_INDICATOR).assertExists()

    }

    @Test
    fun incidentListScreen_display_emptyListTest_whenApiListIsEmpty(){
        fakeRepository.emit(
            Result.Success(
                RfsFeatureCollection(
                    type = "FeatureCollection",
                    features = emptyList()
                )
            )
        )

        composeRule.onNodeWithTag(TestTags.EMPTY_STATE_TEXT).assertExists()

    }



    @Test
    fun incidentListScreen_display_error_whenApiRespondsWithError(){
        fakeRepository.emit(
            Result.Error("Network Error")
        )

        composeRule.onNodeWithTag(TestTags.ERROR_MESSAGE_TEXT).assertExists()

    }


}