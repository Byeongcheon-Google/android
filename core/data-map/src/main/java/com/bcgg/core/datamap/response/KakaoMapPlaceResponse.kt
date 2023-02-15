package com.bcgg.core.datamap.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoMapPlaceResponse(
    @SerialName("documents") val items: List<Items>,
    @SerialName("meta") val meta: Meta
) {
    @Serializable
    data class Items(
        @SerialName("place_name") val name: String,
        @SerialName("category_group_code") val categoryGroupCode: String,
        @SerialName("category_group_name") val categoryGroupName: String,
        @SerialName("category_name") val categoryName: String,
        @SerialName("distance") val distance: String,
        @SerialName("place_url") val placeUrl: String,
        @SerialName("id") val id: String,
        @SerialName("phone") val phone: String,
        @SerialName("address_name") val address: String,
        @SerialName("road_address_name") val roadAddress: String,
        @SerialName("x") val lng: String,
        @SerialName("y") val lat: String
    )

    @Serializable
    data class Meta(
        @SerialName("is_end") val isEnd: Boolean,
        @SerialName("pageable_count") val pageableCount: Int,
        @SerialName("total_count") val totalCount: Int,
        @SerialName("same_name") val sameName: RegionInfo
    ) {
        @Serializable
        data class RegionInfo(
            @SerialName("keyword") val keyword: String,
            @SerialName("region") val region: List<String>,
            @SerialName("selected_region") val selectedRegion: String
        )
    }
}
