package com.bcgg.core.datamap.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NaverMapPlaceResponse(
    @SerialName("lastBuildDate") val lastBuildDate: String,
    @SerialName("total") val total: Int,
    @SerialName("start") val start: Int,
    @SerialName("display") val display: Int,
    @SerialName("items") val items: List<Items>
) {
    @Serializable
    data class Items(
        @SerialName("title") val title: String,
        @SerialName("link") val link: String,
        @SerialName("category") val category: String,
        @SerialName("description") val description: String,
        @SerialName("telephone") val telephone: String,
        @SerialName("address") val address: String,
        @SerialName("roadAddress") val roadAddress: String,
        @SerialName("mapx") val mapX: String,
        @SerialName("mapy") val mapY: String
    )
}
