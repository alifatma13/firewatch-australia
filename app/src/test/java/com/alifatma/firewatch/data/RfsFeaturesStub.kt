package com.alifatma.firewatch.data


internal object RfsFeaturesStub {


    val pointIncidents = listOf(

        // --- Point 1 ---
        RfsFeature(
            type = "Feature",

            geometry = Geometry.Point(
                coordinates = listOf(
                    152.038422,
                    -31.531539
                )
            ),

            properties = RfsProperties(
                title = "CELLS RD, CELLS RIVER",
                category = "Advice",
                guid = "https://incidents.rfs.nsw.gov.au/api/v1/incidents/653602",
                pubDate = "9/04/2026 7:01:00 AM",
                description =
                    "ALERT LEVEL: Advice LOCATION: CELLS RD, CELLS RIVER " +
                            "COUNCIL AREA: Mid-Coast STATUS: Being controlled TYPE: Bush Fire"
            )
        ),

        // --- Point 2 ---
        RfsFeature(
            type = "Feature",

            geometry = Geometry.Point(
                coordinates = listOf(
                    150.321223999,
                    -34.635894
                )
            ),

            properties = RfsProperties(
                title = "BUNDANOON RD, EXETER",
                category = "Not Applicable",
                guid = "https://incidents.rfs.nsw.gov.au/api/v1/incidents/653572",
                pubDate = "9/04/2026 1:45:00 AM",
                description =
                    "ALERT LEVEL: Not Applicable LOCATION: RINGWOOD GLEN " +
                            "STATUS: Under control TYPE: Burn off"
            )
        )

    )

    val multiplePolygonOnlyList = listOf(

        RfsFeature(
            type = "Feature",

            geometry = Geometry.GeometryCollection(
                geometries = listOf(

                    Geometry.GeometryCollection(
                        geometries = listOf(

                            // --- Polygon 1 ---
                            Geometry.Polygon(
                                coordinates = listOf(
                                    listOf(
                                        listOf(148.215287123, -35.508882376),
                                        listOf(148.217432838, -35.509659824),
                                        listOf(148.213258584, -35.520681367),
                                        listOf(148.215287123, -35.508882376)
                                    )
                                )
                            ),

                            // --- Polygon 2 ---
                            Geometry.Polygon(
                                coordinates = listOf(
                                    listOf(
                                        listOf(148.219694429, -35.520976859),
                                        listOf(148.218848812, -35.520535207),
                                        listOf(148.219700018, -35.520930888),
                                        listOf(148.219694429, -35.520976859)
                                    )
                                )
                            ),

                            // --- Polygon 3 ---
                            Geometry.Polygon(
                                coordinates = listOf(
                                    listOf(
                                        listOf(148.215130959, -35.508425132),
                                        listOf(148.213059071, -35.510502615),
                                        listOf(148.212272391, -35.513973974),
                                        listOf(148.215130959, -35.508425132)
                                    )
                                )
                            )

                        )
                    )

                )
            ),

            properties = RfsProperties(
                title = "Polygon Only GeometryCollection",
                category = "Planned Burn",
                guid = "polygon-only-collection-001",
                pubDate = "9/04/2026 6:27:00 AM",
                description = "Polygon-only nested geometry collection."
            )
        )

    )

    val singlePointIncident = listOf(

        RfsFeature(
            type = "Feature",

            geometry = Geometry.Point(
                coordinates = listOf(
                    152.038422,   // lng
                    -31.531539    // lat
                )
            ),

            properties = RfsProperties(
                title = "CELLS RD, CELLS RIVER",
                category = "Advice",
                guid = "https://incidents.rfs.nsw.gov.au/api/v1/incidents/653602",
                pubDate = "9/04/2026 7:01:00 AM",
                description =
                    "ALERT LEVEL: Advice LOCATION: CELLS RD, CELLS RIVER 2424 " +
                            "COUNCIL AREA: Mid-Coast STATUS: Being controlled TYPE: Bush Fire"
            )
        )

    )

    val geometryCollectionIncident = listOf(

        RfsFeature(
            type = "Feature",

            geometry = Geometry.GeometryCollection(
                geometries = listOf(

                    // --- Point inside collection ---
                    Geometry.Point(
                        coordinates = listOf(
                            151.595611572,
                            -29.7227210999999
                        )
                    ),

                    // --- Nested GeometryCollection ---
                    Geometry.GeometryCollection(
                        geometries = listOf(

                            // --- Polygon inside nested collection ---
                            Geometry.Polygon(
                                coordinates = listOf(
                                    listOf(
                                        listOf(151.594336448, -29.721812389),
                                        listOf(151.594443524, -29.721705313),
                                        listOf(151.594574395, -29.721622032),
                                        listOf(151.594705264, -29.7215863399999),
                                        listOf(151.594776649, -29.721622032),
                                        listOf(151.594978903, -29.721764799),
                                        listOf(151.595145465, -29.7218599779999),
                                        listOf(151.595252541, -29.722038437),
                                        listOf(151.59530013, -29.722133616),
                                        listOf(151.595526179, -29.7221931029999),
                                        listOf(151.595597563, -29.7223001789999),
                                        listOf(151.595573768, -29.722371562),
                                        listOf(151.595371513, -29.7224191509999),
                                        listOf(151.595204951, -29.7223358699999),
                                        listOf(151.594824238, -29.7220622319999),
                                        listOf(151.594372141, -29.7219194649999),
                                        listOf(151.594324551, -29.721871875),
                                        listOf(151.594324551, -29.72184808),
                                        listOf(151.594336448, -29.721812389)
                                    )
                                )
                            )
                        )
                    )
                )
            ),

            properties = RfsProperties(
                title = "(GWYDIR HWY), MATHESON",
                category = "Advice",
                guid = "https://incidents.rfs.nsw.gov.au/api/v1/incidents/653509",
                pubDate = "8/04/2026 2:08:00 PM",
                description = "Grass Fire — Under control"
            )
        )
    )
}
