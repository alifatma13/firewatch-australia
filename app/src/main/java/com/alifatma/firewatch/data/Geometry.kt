package com.alifatma.firewatch.data

import com.alifatma.firewatch.data.Geometry.GeometryCollection
import com.alifatma.firewatch.data.Geometry.Point
import com.alifatma.firewatch.data.Geometry.Polygon
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


private val TAG = "GeometryParser"

/**
 * This file defines the Geometry sealed class and its subclasses to represent different types of geometries
 * such as Point, Polygon, and GeometryCollection. It also includes extension functions to extract the center
 * and polygons from the geometry data.
 */
@Serializable
sealed class Geometry {

    @Serializable
    @SerialName("Point")
    data class Point(val coordinates: List<Double>) : Geometry()

    @Serializable
    @SerialName("Polygon")
    data class Polygon(val coordinates: List<List<List<Double>>>) : Geometry()

    @Serializable
    @SerialName("GeometryCollection")
    data class GeometryCollection(val geometries: List<Geometry>) : Geometry()

}

private fun List<Double>.toLatLng() = Coordinates(lat = this[1], lng = this[0])

fun Geometry.extractCenter(): Coordinates? = when (this) {
    is Point -> coordinates.toLatLng()
    is Polygon -> listOf(coordinates[0].map { it.toLatLng() })
    else -> null
} as Coordinates?

fun Geometry.extractPolygons(): List<List<Coordinates>> = when (this) {
    is Polygon -> listOf(coordinates[0].map { it.toLatLng() })
    is GeometryCollection -> geometries.flatMap { it.extractPolygons() }
    else -> emptyList()
}