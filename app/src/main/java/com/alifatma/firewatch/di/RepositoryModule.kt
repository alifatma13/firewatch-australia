package com.alifatma.firewatch.di

import com.alifatma.firewatch.repository.IncidentRepository
import com.alifatma.firewatch.repository.IncidentRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindIncidentRepository(
        incidentRepositoryImpl: IncidentRepositoryImpl
    ): IncidentRepository

}