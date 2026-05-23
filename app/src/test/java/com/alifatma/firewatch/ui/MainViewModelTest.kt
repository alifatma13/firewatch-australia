package com.alifatma.firewatch.ui

import com.alifatma.firewatch.data.RfsFeatureCollection
import com.alifatma.firewatch.data.RfsFeaturesStub.geometryCollectionIncident
import com.alifatma.firewatch.data.RfsFeaturesStub.multiplePolygonOnlyList
import com.alifatma.firewatch.data.RfsFeaturesStub.pointIncidents
import com.alifatma.firewatch.data.RfsFeaturesStub.singlePointIncident
import com.alifatma.firewatch.network.RfsApiService
import com.alifatma.firewatch.repository.IncidentRepositoryImpl
import com.alifatma.firewatch.ui.model.toUiModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `init load keeps uiState loading when repository returns loading`() = runTest {
        val mockApiService : RfsApiService = mockk()
        // Simulate a loading state by making the API call suspend indefinitely
        coEvery { mockApiService.getMajorIncidents() } coAnswers {
            kotlinx.coroutines.delay(Long.MAX_VALUE)
            RfsFeatureCollection(type = "FeatureCollection", features = emptyList())
        }
        // integrated test with real repository and mocked API
        val repository = IncidentRepositoryImpl(mockApiService)

        val viewModel = MainViewModel(repository)
        advanceUntilIdle()
        assertTrue(viewModel.uiState.value is RfsUiState.Loading)
    }

    @Test
    fun `init load updates uiState to error when repository returns error`() = runTest {
        val mockApiService = mockk<RfsApiService>()
        // Simulate an error response from the API
         coEvery { mockApiService.getMajorIncidents() } throws IOException("Network Error")
        // integrated test with real repository and mocked API
        val repository = IncidentRepositoryImpl(mockApiService)

        val viewModel = MainViewModel(repository)
        viewModel.load()
        advanceUntilIdle()

        assertEquals(RfsUiState.Error("Network Error"), viewModel.uiState.value)
    }

    @Test
    fun `init load updates uiState to success when repository returns success`() = runTest {
        val mockApiService = mockk<RfsApiService>()
        coEvery { mockApiService.getMajorIncidents() } returns RfsFeatureCollection(
            type = "FeatureCollection",
            features = singlePointIncident
        )
        // integrated test with real repository and mocked API
        val repository = IncidentRepositoryImpl(mockApiService)

        val viewModel = MainViewModel(repository)
        viewModel.load()
        advanceUntilIdle()

        assertEquals(RfsUiState.Success(singlePointIncident.map{ it.toUiModel()}), viewModel.uiState.value)
        assertFalse(viewModel.uiState.value is RfsUiState.Loading)
        assertFalse(viewModel.uiState.value is RfsUiState.Error)
    }

    @Test
    fun `init load updates uiState to success when incident list is empty`() = runTest {

        val mockApiService = mockk<RfsApiService>()
        coEvery { mockApiService.getMajorIncidents() } returns RfsFeatureCollection(
            type = "FeatureCollection",
            features = emptyList()
        )
        // integrated test with real repository and mocked API
        val repository = IncidentRepositoryImpl(mockApiService)

        val viewModel = MainViewModel(repository)
        viewModel.load()
        advanceUntilIdle()

        assertTrue((viewModel.uiState.value as? RfsUiState.Success)?.incidents?.isEmpty() == true)
        assertFalse(viewModel.uiState.value is RfsUiState.Loading)
        assertFalse(viewModel.uiState.value is RfsUiState.Error)
    }

    @Test
    fun `init load updates uiState to success when incident geometry has polygon only`() = runTest {
        // Create a mock API service
        val mockApiService = mockk<RfsApiService>()
        coEvery { mockApiService.getMajorIncidents() } returns RfsFeatureCollection(
            type = "FeatureCollection",
            features = multiplePolygonOnlyList
        )

        // integrated test with real repository and mocked API
        val repository = IncidentRepositoryImpl(mockApiService)

        val viewModel = MainViewModel(repository)
        viewModel.load()
        advanceUntilIdle()

        when (val state = viewModel.uiState.value) {
            is RfsUiState.Success -> {
                val center = state.incidents.firstOrNull()?.center
                assertTrue(center == null)
                val polygons = state.incidents.firstOrNull()?.polygons
                assertTrue(polygons != null && polygons.isNotEmpty())
            }

            else -> assertTrue(false)
        }

    }

    @Test
    fun `init load updates uiState to success when incident geometry has point only`() = runTest {

        val mockApiService = mockk<RfsApiService>()
        coEvery { mockApiService.getMajorIncidents() } returns RfsFeatureCollection(
            type = "FeatureCollection",
            features = pointIncidents
        )
        // integrated test with real repository and mocked API
        val repository = IncidentRepositoryImpl(mockApiService)


        val viewModel = MainViewModel(repository)
        viewModel.load()
        advanceUntilIdle()

        when (val state = viewModel.uiState.value) {
            is RfsUiState.Success -> {
                //val center = state.incidents.firstOrNull()?.geometry?.extractCenter()
                val center = state.incidents.firstOrNull()?.center
                assertTrue(center != null)
                val polygons = state.incidents.firstOrNull()?.polygons
                assertTrue(polygons != null && polygons.isEmpty())
            }

            else -> assertTrue(false)
        }
    }

    @Test
    fun `load updates uiState to success when incident geometry has both point and polygon`() =
        runTest {
            val mockApiService: RfsApiService = mockk()
            coEvery { mockApiService.getMajorIncidents() } returns RfsFeatureCollection(
                type = "FeatureCollection",
                features = geometryCollectionIncident
            )

            // integrated test with real repository and mocked API
            val repository = IncidentRepositoryImpl(mockApiService)


            val viewModel = MainViewModel(repository)
            viewModel.load()
            advanceUntilIdle()

            when (val state = viewModel.uiState.value) {
                is RfsUiState.Success -> {
                    val center = state.incidents.firstOrNull()?.center
                    assertTrue(center != null)
                    val polygons = state.incidents.firstOrNull()?.polygons
                    assertTrue(polygons != null && polygons.isNotEmpty())
                }

                else -> assertTrue(false)
            }
        }

}