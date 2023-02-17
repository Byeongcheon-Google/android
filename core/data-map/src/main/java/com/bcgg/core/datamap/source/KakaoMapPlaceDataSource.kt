package com.bcgg.core.datamap.source

import com.bcgg.core.datamap.constant.KakaoMapConstant.DEFAULT_SIZE
import com.bcgg.core.datamap.response.KakaoMapPlaceResponse

interface KakaoMapPlaceDataSource {
    suspend fun getPlace(
        query: String,
        lng: String,
        lat: String,
        size: Int = DEFAULT_SIZE,
        page: Int
    ): KakaoMapPlaceResponse
}
