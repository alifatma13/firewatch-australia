package com.alifatma.firewatch.repository

import com.alifatma.firewatch.data.Result
import com.alifatma.firewatch.data.Result.ErrorType
import com.alifatma.firewatch.data.RfsFeatureCollection
import com.alifatma.firewatch.data.RfsFeaturesStub.singlePointIncident
import com.alifatma.firewatch.db.dao.IncidentDao
import com.alifatma.firewatch.db.entity.toEntity
import com.alifatma.firewatch.network.NetworkStatusProvider
import com.alifatma.firewatch.network.RfsApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class IncidentRepositoryImplTest {

    private val mockApiService = mockk<RfsApiService>()
    private val mockDao = mockk<IncidentDao>(relaxed = true)
    private var isOnline = true

    private val repository = IncidentRepositoryImpl(
        mockApiService,
        mockDao,
        object : NetworkStatusProvider {
            override fun isOnline(): Boolean = isOnline
            override fun observeNetworkStatus() = flowOf(isOnline)
        }
    )

    @Test
    fun `getMajorIncidents returns success and caches data when online`() = runTest {
        // Arrange
        isOnline = true
        val features = singlePointIncident
        val expectedData = RfsFeatureCollection(type = "FeatureCollection", features = features)
        coEvery { mockApiService.getMajorIncidents() } returns expectedData

        // Act
        val result = repository.getMajorIncidents()

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(expectedData, (result as Result.Success).data)
        coVerify(exactly = 1) { mockApiService.getMajorIncidents() }
        coVerify(exactly = 1) { mockDao.insertAll(any()) }
    }

    @Test
    fun `getMajorIncidents returns cached data when offline`() = runTest {
        // Arrange
        isOnline = false
        val features = singlePointIncident
        val entities = features.map { it.toEntity() }
        coEvery { mockDao.getAll() } returns entities

        // Act
        val result = repository.getMajorIncidents()

        // Assert
        assertTrue(result is Result.Success)
        val successData = (result as Result.Success).data
        assertEquals(features.size, successData.features.size)
        assertEquals(features[0].properties.guid, successData.features[0].properties.guid)
        coVerify(exactly = 0) { mockApiService.getMajorIncidents() }
        coVerify(exactly = 1) { mockDao.getAll() }
    }

    @Test
    fun `getMajorIncidents returns error when offline and cache is empty`() = runTest {
        // Arrange
        isOnline = false
        coEvery { mockDao.getAll() } returns emptyList()

        // Act
        val result = repository.getMajorIncidents()

        // Assert
        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertEquals(ErrorType.NETWORK, error.errorType)
        assertEquals("No internet connection and no cached data available", error.message)
        coVerify(exactly = 1) { mockDao.getAll() }
    }

    @Test
    fun `getMajorIncidents returns error when IOException occurs`() = runTest {
        isOnline = true
        val ioException = IOException("Socket timeout")
        coEvery { mockApiService.getMajorIncidents() } throws ioException

        val result = repository.getMajorIncidents()

        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertEquals(ErrorType.NETWORK, error.errorType)
        assertEquals(ioException, error.exception)
    }

    @Test
    fun `getMajorIncidents returns error when HttpException occurs`() = runTest {
        isOnline = true
        val httpException = HttpException(Response.error<String>(500, "".toResponseBody("text/plain".toMediaType())))
        coEvery { mockApiService.getMajorIncidents() } throws httpException

        val result = repository.getMajorIncidents()

        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertEquals(ErrorType.HTTP, error.errorType)
        assertEquals(httpException, error.exception)
    }

    @Test
    fun `getMajorIncidents returns error when API endpoint not found (404)`() = runTest {
        isOnline = true
        val notFoundException = HttpException(Response.error<String>(404, "".toResponseBody("text/html".toMediaType())))
        coEvery { mockApiService.getMajorIncidents() } throws notFoundException

        val result = repository.getMajorIncidents()

        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertEquals(ErrorType.HTTP, error.errorType)
        assertEquals(notFoundException, error.exception)
    }
}
