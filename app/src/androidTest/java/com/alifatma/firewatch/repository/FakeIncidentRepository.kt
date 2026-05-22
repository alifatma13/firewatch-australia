package com.alifatma.firewatch.repository

import com.alifatma.firewatch.data.Result
import com.alifatma.firewatch.data.RfsFeatureCollection
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.delay


class FakeIncidentRepository : IncidentRepository {

    private var deferred = CompletableDeferred<Result<RfsFeatureCollection>>()


    override suspend fun getMajorIncidents(): Result<RfsFeatureCollection> {
        return deferred.await()
    }

    fun emit(result: Result<RfsFeatureCollection>) {
        deferred.complete(result)
    }

    fun reset() {
        deferred = CompletableDeferred()
    }


}

