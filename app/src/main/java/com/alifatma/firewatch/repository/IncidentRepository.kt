package com.alifatma.firewatch.repository

import com.alifatma.firewatch.data.RfsFeatureCollection
import com.alifatma.firewatch.data.Result

interface IncidentRepository {

    suspend fun getMajorIncidents() : Result<RfsFeatureCollection>

}