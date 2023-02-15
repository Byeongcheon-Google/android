package com.bcgg.core.datamap.source.retrofit

import com.bcgg.core.datamap.response.NaverMapPlaceResponse
import com.bcgg.core.datamap.source.NaverMapPlaceDataSource
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitNaverMapPlaceDataSource : NaverMapPlaceDataSource {
    @GET("v1/search/local.json")
    override suspend fun getPlace(
        @Query("query") query: String,
        @Query("display") display: Int
    ): NaverMapPlaceResponse
}
