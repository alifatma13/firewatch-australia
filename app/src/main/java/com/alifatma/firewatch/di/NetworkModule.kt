package com.alifatma.firewatch.di


import com.alifatma.firewatch.BuildConfig
import com.alifatma.firewatch.network.RfsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Named
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = BuildConfig.RFS_NSW_GOV_AU

    //Logging Interceptor

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
        }
    }

    //OkHttpClient
    @Provides
    @Singleton
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }


    // Converter Factory
    @Provides
    @Singleton
    fun providesConverterFactory(): Converter.Factory {

        val json: Json by lazy {
            Json {
                classDiscriminator = "type"
                ignoreUnknownKeys = true
            }
        }
        return json.asConverterFactory("application/json".toMediaType())
    }

    // Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    }

    //API Service
    @Provides
    @Singleton
    fun providesRfsApiService(retrofit: Retrofit) : RfsApiService{
        return retrofit.create<RfsApiService>(RfsApiService::class.java)
    }


}