package com.alifatma.firewatch.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@JsonIgnoreUnknownKeys
@Serializable
data class RfsProperties(
    val title: String = "",
    val category: String = "",
    val guid: String = "",
    val pubDate: String = "",
    val description: String = ""
)