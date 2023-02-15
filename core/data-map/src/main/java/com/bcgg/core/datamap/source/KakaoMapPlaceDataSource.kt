package com.bcgg.core.datamap.source

import com.bcgg.core.datamap.response.KakaoMapPlaceResponse

interface KakaoMapPlaceDataSource {
    suspend fun getPlace(
        query: String,
        lng: String,
        lat: String,
        radius: Int = 10000,
        page: Int
    ): KakaoMapPlaceResponse
}
