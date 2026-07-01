package com.alifatma.firewatch.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alifatma.firewatch.db.entity.IncidentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IncidentDao {

    /** Observe all cached incidents — emits automatically when data changes */
    @Query("SELECT * FROM incidents ORDER BY lastUpdated DESC")
    fun observeAll(): Flow<List<IncidentEntity>>

    /** One-shot read — used when you just need current cache without observing */
    @Query("SELECT * FROM incidents ORDER BY lastUpdated DESC")
    suspend fun getAll(): List<IncidentEntity>

    /** Insert or replace on conflict — handles re-sync cleanly */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(incidents: List<IncidentEntity>)

    /** Returns timestamp of the most recently cached incident, or null if empty */
    @Query("SELECT MAX(lastUpdated) FROM incidents")
    suspend fun getLastUpdatedTime(): Long?

    /** Clear all cached data — useful for forced full refresh */
    @Query("DELETE FROM incidents")
    suspend fun clearAll()

    /** Delete incidents older than a specific timestamp (e.g., 30 days) */
    @Query("DELETE FROM incidents WHERE lastUpdated < :threshold")
    suspend fun deleteOldIncidents(threshold: Long)
}