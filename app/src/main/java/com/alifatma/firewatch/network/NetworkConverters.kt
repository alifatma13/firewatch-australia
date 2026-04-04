package com.alifatma.firewatch.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter

object NetworkConverters {

    val json: Json by lazy {
        Json {
            classDiscriminator = "type"
            ignoreUnknownKeys = true
        }
    }

    val converterFactory: Converter.Factory by lazy {
        json.asConverterFactory(
            "application/json".toMediaType()
        )
    }

}