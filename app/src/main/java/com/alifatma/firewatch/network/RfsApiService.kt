package com.alifatma.firewatch.network

import com.alifatma.firewatch.data.RfsFeatureCollection
import retrofit2.http.GET

interface RfsApiService {

    @GET("feeds/majorIncidents.json")
    suspend fun getMajorIncidents() : RfsFeatureCollection

}