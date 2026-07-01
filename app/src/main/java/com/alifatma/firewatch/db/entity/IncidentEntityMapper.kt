package com.alifatma.firewatch.db.entity

import com.alifatma.firewatch.data.Geometry
import com.alifatma.firewatch.data.RfsFeature
import com.alifatma.firewatch.data.RfsProperties
import com.alifatma.firewatch.data.parseDescription
import kotlinx.serialization.json.Json

fun RfsFeature.toEntity(): IncidentEntity {
    val details = properties.parseDescription()

    return IncidentEntity(
        guid = properties.guid,
        type = type,
        title = properties.title,
        category = properties.category,
        pubDate = properties.pubDate,
        description = properties.description,
        status = details.status,
        responsibleAgency = details.responsibleAgency,
        alertLevel = details.alertLevel,
        councilArea = details.councilArea,
        incidentType = details.type,
        location = details.location,
        size = details.size,
        updated = details.updated,
        geometryJson = geometry?.let { Json.encodeToString(it) }
    )
}

// IncidentEntity → RfsFeature (for serving cached data back to the UI)
fun IncidentEntity.toFeature(): RfsFeature {
    // Rebuild the description string from stored fields
    val descriptionParts = mutableListOf<String>()

    alertLevel?.let { descriptionParts.add("ALERT LEVEL: $it") }
    location?.let { descriptionParts.add("LOCATION: $it") }
    councilArea?.let { descriptionParts.add("COUNCIL AREA: $it") }
    status?.let { descriptionParts.add("STATUS: $it") }
    incidentType?.let { descriptionParts.add("TYPE: $it") }
    size?.let { descriptionParts.add("SIZE: $it") }
    responsibleAgency?.let { descriptionParts.add("RESPONSIBLE AGENCY: $it") }
    updated?.let { descriptionParts.add("UPDATED: $it") }

    val rebuiltDescription = if (descriptionParts.isNotEmpty()) {
        descriptionParts.joinToString(separator = "<br />")
    } else {
        description // fallback to original if no parsed fields exist
    }

    return RfsFeature(
        type = type,
        geometry = geometryJson?.let { Json.decodeFromString<Geometry>(it) },
        properties = RfsProperties(
            guid = guid,
            title = title,
            category = category,
            pubDate = pubDate,
            description = rebuiltDescription
        )
    )
}