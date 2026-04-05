package com.alifatma.firewatch.repository

import com.alifatma.firewatch.data.RfsFeatureCollection
import com.alifatma.firewatch.network.RfsApiService
import jakarta.inject.Inject


class IncidentRepositoryImpl
@Inject constructor(
    private val rfsApiService: RfsApiService
) : IncidentRepository {

    override suspend fun getMajorIncidents(): Result<RfsFeatureCollection> {
        return try {
            val result = rfsApiService.getMajorIncidents()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}