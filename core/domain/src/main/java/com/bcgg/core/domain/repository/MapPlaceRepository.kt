package com.bcgg.core.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.bcgg.core.datamap.constant.KakaoMapConstant
import com.bcgg.core.datamap.source.KakaoMapPlaceDataSource
import com.bcgg.core.datamap.source.paging.KakaoMapSearchPagingSource
import com.bcgg.core.domain.model.MapSearchResult
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MapPlaceRepository @Inject constructor(
    private val kakaoMapPlaceDataSource: KakaoMapPlaceDataSource
) {
    fun getPlace(
        query: String,
        lng: Double,
        lat: Double
    ) = Pager(
        PagingConfig(pageSize = KakaoMapConstant.DEFAULT_SIZE)
    ) {
        KakaoMapSearchPagingSource(
            kakaoMapPlaceDataSource, query, lng.toString(), lat.toString()
        )
    }.flow
        .map {
            it.map {
                MapSearchResult(
                    id = it.id,
                    name = it.name,
                    address = it.address,
                    lat = it.lat.toDouble(),
                    lng = it.lng.toDouble()
                )
            }
        }
}
