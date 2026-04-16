package com.alifatma.firewatch.repository

import com.alifatma.firewatch.data.Result
import com.alifatma.firewatch.data.RfsFeatureCollection
import com.alifatma.firewatch.network.RfsApiService
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException

import okio.IOException
import retrofit2.HttpException


class IncidentRepositoryImpl
@Inject constructor(
    private val rfsApiService: RfsApiService
) : IncidentRepository {
    override suspend fun getMajorIncidents(): Result<RfsFeatureCollection> {
        try {
            val response = rfsApiService.getMajorIncidents()
            return Result.Success(response)
        } catch (e: CancellationException) {
            // Do not convert cancellation into a business error.
            throw e
        } catch (e: IOException) {
            return Result.Error("Network error: ${e.message}", e)
        } catch (e: HttpException) {
            return Result.Error("HTTP error: ${e.message}", e)
        } catch (e: Exception) {
            return Result.Error("Unexpected error: ${e.message}", e)
        }
    }


}