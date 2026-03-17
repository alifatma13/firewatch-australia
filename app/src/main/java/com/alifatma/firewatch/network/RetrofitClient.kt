package com.alifatma.firewatch.network

import com.alifatma.firewatch.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private const val RFS_NSW_GOV_AU = BuildConfig.RFS_NSW_GOV_AU

object RetrofitClient {
    val json = Json {
        classDiscriminator = "type"
        ignoreUnknownKeys = true
    }
    val api: RfsApiService = Retrofit.Builder().baseUrl(RFS_NSW_GOV_AU).client(
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }).build()
    ).addConverterFactory(
        json.asConverterFactory("application/json".toMediaType())
    ).build().create(RfsApiService::class.java)
}