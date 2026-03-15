package com.alifatma.firewatch.data

import kotlinx.serialization.Serializable

@Serializable
data class RfsFeatureCollection( val type : String, val features : List<RfsFeature>)
