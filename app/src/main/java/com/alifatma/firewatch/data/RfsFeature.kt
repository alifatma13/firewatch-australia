package com.alifatma.firewatch.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
data class RfsFeature(val type: String, val geometry : JsonElement?=null, val properties : RfsProperties)
