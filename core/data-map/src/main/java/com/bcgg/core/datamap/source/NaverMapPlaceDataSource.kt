package com.bcgg.core.datamap.source

import com.bcgg.core.datamap.response.NaverMapPlaceResponse

interface NaverMapPlaceDataSource {
    suspend fun getPlace(
        query: String,
        display: Int,
    ): NaverMapPlaceResponse
}
