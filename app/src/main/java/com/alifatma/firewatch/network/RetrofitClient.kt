package com.alifatma.firewatch.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitClient {
    val api: RfsApiService = Retrofit.Builder().baseUrl("https://www.rfs.nsw.gov.au/").client(
            OkHttpClient.Builder().addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }).build()).addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType())
        ).build().create(RfsApiService::class.java)
}