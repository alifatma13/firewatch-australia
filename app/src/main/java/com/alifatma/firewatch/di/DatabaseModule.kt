package com.alifatma.firewatch.di

import android.content.Context
import androidx.room.Room
import com.alifatma.firewatch.db.AppDatabase
import com.alifatma.firewatch.db.dao.IncidentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "firewatch.db"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()

    @Provides
    @Singleton
    fun provideIncidentDao(database: AppDatabase): IncidentDao =
        database.incidentDao()
}