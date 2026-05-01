package com.alifatma.firewatch.ui.model

import com.alifatma.firewatch.data.RfsFeature
import com.alifatma.firewatch.data.extractCenter
import com.alifatma.firewatch.data.extractIncidentId
import com.alifatma.firewatch.data.extractPolygons
import com.alifatma.firewatch.data.parseDescription

/*
    * Extension function to convert RfsFeature to FireIncidentUiModel
 */
fun RfsFeature.toUiModel(): FireIncidentUiModel {
    val details = this.properties.parseDescription()
    val id = this.properties.extractIncidentId()
    val center = this.geometry?.extractCenter()
    val polygons = this.geometry?.extractPolygons()
    return FireIncidentUiModel(
        id = id,
        title = this.properties.title,
        category = this.properties.category,
        pubDate = this.properties.pubDate,
        location = details.location,
        status = details.status,
        responsibleAgency = details.responsibleAgency,
        councilArea = details.councilArea,
        alertLevel = details.alertLevel,
        type = details.type,
        size = details.size,
        updated = details.updated,
        center = center,
        polygons = polygons,
        extras = details.extras
    )
}