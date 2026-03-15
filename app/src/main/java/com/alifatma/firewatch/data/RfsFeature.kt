package com.alifatma.firewatch.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class RfsFeature(val type: String, val geometry : JsonElement?=null, val properties : RfsProperties)
