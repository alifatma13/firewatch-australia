package com.alifatma.firewatch.network

import com.alifatma.firewatch.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

val loggingInterceptor: HttpLoggingInterceptor by lazy {
    HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }
}