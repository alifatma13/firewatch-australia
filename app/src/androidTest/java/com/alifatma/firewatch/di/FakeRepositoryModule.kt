package com.alifatma.firewatch.di

import com.alifatma.firewatch.repository.FakeIncidentRepository
import com.alifatma.firewatch.repository.IncidentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import jakarta.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object FakeRepositoryModule {

    val fakeRepository = FakeIncidentRepository()

    @Provides
    @Singleton
    fun provideIncidentRepository(): IncidentRepository {
       return fakeRepository
    }
}