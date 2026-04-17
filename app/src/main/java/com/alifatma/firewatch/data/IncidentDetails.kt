package com.alifatma.firewatch.data

/**
 * Parsed incident details extracted from the semi-structured description field.
 * Known fields are optional (nullable), unknown fields are stored in extras map.
 */
data class IncidentDetails(
    val alertLevel: String? = null,
    val location: String? = null,
    val councilArea: String? = null,
    val status: String? = null,
    val type: String? = null,
    val fire: Boolean? = null,
    val size: String? = null,
    val responsibleAgency: String? = null,
    val updated: String? = null,
    val extras: Map<String, String> = emptyMap()
)

