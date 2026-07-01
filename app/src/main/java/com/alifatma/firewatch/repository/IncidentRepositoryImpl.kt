package com.alifatma.firewatch.repository

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.alifatma.firewatch.data.Result
import com.alifatma.firewatch.data.Result.ErrorType
import com.alifatma.firewatch.data.RfsFeatureCollection
import com.alifatma.firewatch.db.dao.IncidentDao
import com.alifatma.firewatch.db.entity.toEntity
import com.alifatma.firewatch.db.entity.toFeature
import com.alifatma.firewatch.network.AndroidNetworkStatusProvider
import com.alifatma.firewatch.network.NetworkStatusProvider
import com.alifatma.firewatch.network.RfsApiService
import jakarta.inject.Inject
import okio.IOException
import retrofit2.HttpException


class IncidentRepositoryImpl
@Inject constructor(
    private val rfsApiService: RfsApiService,
    private val incidentDao: IncidentDao,
    private val networkStatusProvider: NetworkStatusProvider

) : IncidentRepository {
    override suspend fun getMajorIncidents(): Result<RfsFeatureCollection> {
        return if (networkStatusProvider.isOnline()) {
            try {
                val response = rfsApiService.getMajorIncidents()
                val entities = response.features.map { it.toEntity() }
                incidentDao.insertAll(entities)
                return Result.Success(response)
            } catch (e: IOException) {
                return Result.Error(
                    message = e.message.orEmpty(),
                    exception = e,
                    errorType = ErrorType.NETWORK
                )
            } catch (e: HttpException) {
                return Result.Error(
                    message = e.message.orEmpty(),
                    exception = e,
                    errorType = ErrorType.HTTP
                )
            }
        }
        else {
            // Offline - serve from Room cache
            val cached = incidentDao.getAll()
            if (cached.isNotEmpty()) {
                Result.Success(RfsFeatureCollection(
                    type = "FeatureCollection",
                    features = cached.map { it.toFeature() }
                ))
            } else {
                Result.Error(
                    message = "No internet connection and no cached data available",
                    errorType = ErrorType.NETWORK
                )
            }
        }
    }

    override suspend fun getLastSyncTime(): Long? {
        return incidentDao.getLastUpdatedTime()
    }



}