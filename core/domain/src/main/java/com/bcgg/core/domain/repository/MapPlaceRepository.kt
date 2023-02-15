package com.bcgg.core.domain.repository

import com.bcgg.core.datamap.source.NaverMapPlaceDataSource
import com.bcgg.core.domain.constant.MapConstant.MAP_SEARCH_DISPLAY_SIZE
import com.bcgg.core.domain.model.MapSearchResult
import javax.inject.Inject

class MapPlaceRepository @Inject constructor(
    private val naverMapPlaceDataSource: NaverMapPlaceDataSource
) {
    suspend fun getPlace(query: String): List<MapSearchResult> {
        val response = naverMapPlaceDataSource.getPlace(query, MAP_SEARCH_DISPLAY_SIZE)

        return response.items.map {
            MapSearchResult(
                name = it.title,
                address = it.address,
                katechMapX = it.mapX,
                katechMapY = it.mapY,
            )
        }
    }
}
