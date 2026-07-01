package com.alifatma.firewatch.repository

import com.alifatma.firewatch.data.Result
import com.alifatma.firewatch.data.RfsFeatureCollection
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.delay


class FakeIncidentRepository : IncidentRepository {

    private var deferred = CompletableDeferred<Result<RfsFeatureCollection>>()
    private var lastSyncTime: Long? = null


    override suspend fun getMajorIncidents(): Result<RfsFeatureCollection> {
        return deferred.await()
    }

    override suspend fun getLastSyncTime(): Long? {
        return lastSyncTime
    }

    fun emit(result: Result<RfsFeatureCollection>) {
        deferred.complete(result)
    }

    fun setLastSyncTime(time: Long?) {
        lastSyncTime = time
    }

    fun reset() {
        deferred = CompletableDeferred()
        lastSyncTime = null
    }


}

