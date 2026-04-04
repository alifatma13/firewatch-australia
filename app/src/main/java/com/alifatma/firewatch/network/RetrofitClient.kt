package com.alifatma.firewatch.network

import com.alifatma.firewatch.BuildConfig
import retrofit2.Retrofit

private const val RFS_NSW_GOV_AU = BuildConfig.RFS_NSW_GOV_AU

object RetrofitClient {

    val api: RfsApiService =
        Retrofit.Builder().baseUrl(RFS_NSW_GOV_AU)
            .client(NetworkClient.okHttpClient)
            .addConverterFactory(NetworkConverters.converterFactory)
            .build().create(
                RfsApiService::
                class.java
            )
}
