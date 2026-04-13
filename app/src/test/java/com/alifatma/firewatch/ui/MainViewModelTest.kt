package com.alifatma.firewatch.ui

import com.alifatma.firewatch.data.Result
import com.alifatma.firewatch.data.RfsFeatureCollection
import com.alifatma.firewatch.data.RfsFeaturesStub.geometryCollectionIncident
import com.alifatma.firewatch.data.RfsFeaturesStub.multiplePolygonOnlyList
import com.alifatma.firewatch.data.RfsFeaturesStub.pointIncidents
import com.alifatma.firewatch.data.RfsFeaturesStub.singlePointIncident
import com.alifatma.firewatch.data.extractCenter
import com.alifatma.firewatch.data.extractPolygons
import com.alifatma.firewatch.repository.IncidentRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `init load keeps uiState loading when repository returns loading`() = runTest {
        val repository = mockk<IncidentRepository>()
        coEvery { repository.getMajorIncidents() } returns Result.Loading

        val viewModel = MainViewModel(repository)
        advanceUntilIdle()

        coVerify(exactly = 1) { repository.getMajorIncidents() }
        assertTrue(viewModel.uiState.value is RfsUiState.Loading)
    }

    @Test
    fun `init load updates uiState to error when repository returns error`() = runTest {
        val repository = mockk<IncidentRepository>()
        coEvery { repository.getMajorIncidents() } returns Result.Error(message = "network error")

        val viewModel = MainViewModel(repository)
        advanceUntilIdle()

        // do not have to verify whether get major incidents is getting called or not
            // testing behaviour not code structure
        coVerify(exactly = 1) { repository.getMajorIncidents() }
        assertEquals(RfsUiState.Error("network error"), viewModel.uiState.value)
    }

    @Test
    fun `init load updates uiState to success when repository returns success`() = runTest {
        val repository = mockk<IncidentRepository>()
        coEvery { repository.getMajorIncidents() } returns Result.Success(
            RfsFeatureCollection(type = "FeatureCollection", features = singlePointIncident)
        )

        val viewModel = MainViewModel(repository)
        advanceUntilIdle()

        coVerify(exactly = 1) { repository.getMajorIncidents() }
        assertEquals(RfsUiState.Success(singlePointIncident), viewModel.uiState.value)
        assertFalse(viewModel.uiState.value is RfsUiState.Loading)
        assertFalse(viewModel.uiState.value is RfsUiState.Error)
    }

    @Test
    fun `init load updates uiState to success when incident list is empty`() = runTest {
        val repository = mockk<IncidentRepository>()
        coEvery { repository.getMajorIncidents() } returns Result.Success(
            RfsFeatureCollection(type = "FeatureCollection", features = emptyList())
        )

        val viewModel = MainViewModel(repository)
        advanceUntilIdle()

        coVerify(exactly = 1) { repository.getMajorIncidents() }
        assertTrue((viewModel.uiState.value as? RfsUiState.Success)?.incidents?.isEmpty() == true)
        assertFalse(viewModel.uiState.value is RfsUiState.Loading)
        assertFalse(viewModel.uiState.value is RfsUiState.Error)
    }

    @Test
    fun `init load updates uiState to success when incident geometry has polygon only`() = runTest {

        // create real instance of the repository and pass the mocked api
        // integrated test
        // sociable test
        val repository = mockk<IncidentRepository>()
        coEvery { repository.getMajorIncidents() } returns Result.Success(
            RfsFeatureCollection(type = "FeatureCollection", features = multiplePolygonOnlyList)
        )

        val viewModel = MainViewModel(repository)
        advanceUntilIdle()

        when (val state = viewModel.uiState.value) {
            is RfsUiState.Success -> {
                val center = state.incidents.firstOrNull()?.geometry?.extractCenter()
                assertTrue(center == null)
                val polygons = state.incidents.firstOrNull()?.geometry?.extractPolygons()
                assertTrue(polygons != null && polygons.isNotEmpty())
            }

            else -> assertTrue(false)
        }

    }

    @Test
    fun `init load updates uiState to success when incident geometry has point only`() = runTest {
        val repository = mockk<IncidentRepository>()
        coEvery { repository.getMajorIncidents() } returns Result.Success(
            RfsFeatureCollection(type = "FeatureCollection", features = pointIncidents)
        )

        val viewModel = MainViewModel(repository)
        advanceUntilIdle()

        when (val state = viewModel.uiState.value) {
            is RfsUiState.Success -> {
                val center = state.incidents.firstOrNull()?.geometry?.extractCenter()
                assertTrue(center != null)
                val polygons = state.incidents.firstOrNull()?.geometry?.extractPolygons()
                assertTrue(polygons != null && polygons.isEmpty())
            }

            else -> assertTrue(false)
        }
    }

    @Test
    fun `load updates uiState to success when incident geometry has both point and polygon`() =
        runTest {
            val repository = mockk<IncidentRepository>()
            coEvery { repository.getMajorIncidents() } returns Result.Success(
                RfsFeatureCollection(
                    type = "FeatureCollection",
                    features = geometryCollectionIncident
                )
            )

            val viewModel = MainViewModel(repository)
            advanceUntilIdle()

            when (val state = viewModel.uiState.value) {
                is RfsUiState.Success -> {
                    val center = state.incidents.firstOrNull()?.geometry?.extractCenter()
                    assertTrue(center != null)
                    val polygons = state.incidents.firstOrNull()?.geometry?.extractPolygons()
                    assertTrue(polygons != null && polygons.isNotEmpty())
                }

                else -> assertTrue(false)
            }
        }

}