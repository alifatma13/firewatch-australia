package com.alifatma.firewatch.repository

import com.alifatma.firewatch.data.Result
import com.alifatma.firewatch.data.Result.ErrorType
import com.alifatma.firewatch.data.RfsFeatureCollection
import com.alifatma.firewatch.network.RfsApiService
import jakarta.inject.Inject
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
        } catch (e: IOException) {
            return Result.Error(message = e.message.orEmpty(), exception = e, errorType = ErrorType.NETWORK)
        } catch (e: HttpException) {
            return Result.Error(message = e.message.orEmpty(), exception = e, errorType = ErrorType.HTTP)
        }
    }


}