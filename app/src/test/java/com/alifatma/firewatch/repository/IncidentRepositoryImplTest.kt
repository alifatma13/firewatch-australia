package com.alifatma.firewatch.repository

import com.alifatma.firewatch.data.Result
import com.alifatma.firewatch.data.RfsFeatureCollection
import com.alifatma.firewatch.data.RfsFeaturesStub.singlePointIncident
import com.alifatma.firewatch.network.RfsApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class IncidentRepositoryImplTest {

    private val mockApiService = mockk<RfsApiService>()
    private val repository = IncidentRepositoryImpl(mockApiService)

    @Test
    fun `getMajorIncidents returns success when API call succeeds`() = runTest {
        val expectedData = RfsFeatureCollection(type = "FeatureCollection", features = singlePointIncident)
        coEvery { mockApiService.getMajorIncidents() } returns expectedData

        val result = repository.getMajorIncidents()

        assertTrue(result is Result.Success)
        assertEquals(expectedData, (result as Result.Success).data)
    }

    @Test
    fun `getMajorIncidents returns error when IOException occurs`() = runTest {
        val ioException = IOException("Socket timeout")
        coEvery { mockApiService.getMajorIncidents() } throws ioException

        val result = repository.getMajorIncidents()

        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertTrue(error.message.contains("Network error"))
        assertTrue(error.message.contains("Socket timeout"))
        assertEquals(ioException, error.exception)
    }

    @Test
    fun `getMajorIncidents returns error when HttpException occurs`() = runTest {
        val httpException = HttpException(Response.error<String>(500, "".toResponseBody("text/plain".toMediaType())))
        coEvery { mockApiService.getMajorIncidents() } throws httpException

        val result = repository.getMajorIncidents()

        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertTrue(error.message.contains("HTTP error"))
        assertEquals(httpException, error.exception)
    }

    @Test
    fun `getMajorIncidents returns error when API endpoint not found (404)`() = runTest {
        val notFoundException = HttpException(Response.error<String>(404, "".toResponseBody("text/html".toMediaType())))
        coEvery { mockApiService.getMajorIncidents() } throws notFoundException

        val result = repository.getMajorIncidents()

        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertTrue(error.message.contains("HTTP error"))
        assertEquals(notFoundException, error.exception)
    }

    @Test
    fun `getMajorIncidents returns error when unexpected exception occurs`() = runTest {
        val unexpectedException = RuntimeException("Database error")
        coEvery { mockApiService.getMajorIncidents() } throws unexpectedException

        val result = repository.getMajorIncidents()

        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertTrue(error.message.contains("Unexpected error"))
        assertEquals(unexpectedException, error.exception)
    }

}

