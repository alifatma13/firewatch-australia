package com.alifatma.firewatch.data

import com.alifatma.firewatch.data.Geometry.GeometryCollection
import com.alifatma.firewatch.data.Geometry.Point
import com.alifatma.firewatch.data.Geometry.Polygon
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class GeometryExtensionsTest {

    @Test
    fun `extractCenter returns mapped coordinate for Point`() {
        val geometry = Point(coordinates = listOf(151.2093, -33.8688))

        val center = geometry.extractCenter()

        assertEquals(Coordinates(lat = -33.8688, lng = 151.2093), center)
    }

    @Test
    fun `extractCenter returns null for Polygon`() {
        val geometry = Polygon(
            coordinates = listOf(
                listOf(
                    listOf(151.2093, -33.8688),
                    listOf(151.2193, -33.8688),
                    listOf(151.2093, -33.8688)
                )
            )
        )

        assertNull(geometry.extractCenter())
    }

    @Test
    fun `extractCenter returns first available center in nested GeometryCollection`() {
        val geometry = GeometryCollection(
            geometries = listOf(
                Polygon(
                    coordinates = listOf(
                        listOf(
                            listOf(151.0, -33.0),
                            listOf(151.1, -33.1),
                            listOf(151.0, -33.0)
                        )
                    )
                ),
                GeometryCollection(
                    geometries = listOf(
                        Point(coordinates = listOf(150.5, -32.5))
                    )
                )
            )
        )

        val center = geometry.extractCenter()

        assertEquals(Coordinates(lat = -32.5, lng = 150.5), center)
    }

    @Test
    fun `extractPolygons returns empty list for Point`() {
        val geometry = Point(coordinates = listOf(151.2093, -33.8688))

        assertTrue(geometry.extractPolygons().isEmpty())
    }

    @Test
    fun `extractPolygons maps polygon outer ring to coordinates`() {
        val geometry = Polygon(
            coordinates = listOf(
                listOf(
                    listOf(151.2093, -33.8688),
                    listOf(151.2193, -33.8688),
                    listOf(151.2193, -33.8788),
                    listOf(151.2093, -33.8688)
                )
            )
        )

        val polygons = geometry.extractPolygons()

        assertEquals(1, polygons.size)
        assertEquals(
            listOf(
                Coordinates(lat = -33.8688, lng = 151.2093),
                Coordinates(lat = -33.8688, lng = 151.2193),
                Coordinates(lat = -33.8788, lng = 151.2193),
                Coordinates(lat = -33.8688, lng = 151.2093)
            ),
            polygons.first()
        )
    }

    @Test
    fun `extractPolygons flattens nested GeometryCollection polygons`() {
        val geometry = GeometryCollection(
            geometries = listOf(
                Point(coordinates = listOf(151.0, -33.0)),
                GeometryCollection(
                    geometries = listOf(
                        Polygon(
                            coordinates = listOf(
                                listOf(
                                    listOf(148.0, -35.0),
                                    listOf(148.1, -35.1),
                                    listOf(148.0, -35.0)
                                )
                            )
                        ),
                        Polygon(
                            coordinates = listOf(
                                listOf(
                                    listOf(149.0, -36.0),
                                    listOf(149.1, -36.1),
                                    listOf(149.0, -36.0)
                                )
                            )
                        )
                    )
                )
            )
        )

        val polygons = geometry.extractPolygons()

        assertEquals(2, polygons.size)
        assertEquals(3, polygons[0].size)
        assertEquals(3, polygons[1].size)
    }
}

