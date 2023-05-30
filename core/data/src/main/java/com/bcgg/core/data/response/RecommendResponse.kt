package com.bcgg.core.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendResponse(
    @SerialName("name") val name: String,
    @SerialName("address") val address: String,
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("type") val type: String
)