package com.alifatma.firewatch.repository

import com.alifatma.firewatch.data.RfsFeatureCollection

interface IncidentRepository {

    suspend fun getMajorIncidents() : Result<RfsFeatureCollection>

}