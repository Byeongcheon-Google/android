package com.bcgg.core.datamap.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bcgg.core.datamap.response.KakaoMapPlaceResponse
import com.bcgg.core.datamap.source.KakaoMapPlaceDataSource

class KakaoMapSearchPagingSource(
    private val kakaoMapPlaceDataSource: KakaoMapPlaceDataSource,
    private val query: String,
    private val lng: String,
    private val lat: String
) : PagingSource<Int, KakaoMapPlaceResponse.Items>() {
    override fun getRefreshKey(state: PagingState<Int, KakaoMapPlaceResponse.Items>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KakaoMapPlaceResponse.Items> {
        val nextPageNumber = params.key ?: 1
        val response = kakaoMapPlaceDataSource.getPlace(
            query = query,
            lng = lng,
            lat = lat,
            page = nextPageNumber
        )

        return LoadResult.Page(
            data = response.items,
            prevKey = null, // Only paging forward.
            nextKey = if (!response.meta.isEnd) nextPageNumber + 1 else null
        )
    }
}
