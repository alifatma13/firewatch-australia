package com.alifatma.firewatch.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alifatma.firewatch.db.dao.IncidentDao
import com.alifatma.firewatch.db.entity.IncidentEntity

@Database(
    entities = [IncidentEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun incidentDao(): IncidentDao
}