package com.bcgg.core.domain.repository

import com.bcgg.core.datamap.source.KakaoMapPlaceDataSource
import com.bcgg.core.domain.model.MapSearchResult
import javax.inject.Inject

class MapPlaceRepository @Inject constructor(
    private val kakaoMapPlaceDataSource: KakaoMapPlaceDataSource
) {
    suspend fun getPlace(
        query: String,
        lng: Double,
        lat: Double,
        page: Int
    ): List<MapSearchResult> {

        val response = kakaoMapPlaceDataSource.getPlace(
            query = query,
            lng = lng.toString(),
            lat = lat.toString(),
            page = page
        )

        return response.items.map {
            MapSearchResult(
                name = it.name,
                address = it.address,
                lat = it.lat.toDouble(),
                lng = it.lng.toDouble()
            )
        }
    }
}
