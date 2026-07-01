package com.alifatma.firewatch.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity that caches a single RfsFeature from the API.
 * guid is the unique identifier for each incident.
 * geometryJson stores the serialized Geometry as a JSON string
 * since Room can't store nested objects natively.
 * lastUpdated tracks when this row was last synced from the network.
 */
@Entity(tableName = "incidents")
data class IncidentEntity(
    @PrimaryKey
    val guid: String,
    val type: String,
    val title: String,
    val category: String,
    val pubDate: String,
    val description: String,

    val status: String?,
    val responsibleAgency: String?,
    val alertLevel: String?,
    val councilArea: String?,
    val incidentType: String?,
    val updated: String?,
    val size: String?,
    val location: String?,

    val geometryJson: String?,
    val lastUpdated: Long = System.currentTimeMillis()
)