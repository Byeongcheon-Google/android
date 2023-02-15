package com.bcgg.core.datamap.source.retrofit

import com.bcgg.core.datamap.response.KakaoMapPlaceResponse
import com.bcgg.core.datamap.source.KakaoMapPlaceDataSource
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitKakaoMapPlaceDataSource : KakaoMapPlaceDataSource {
    @GET("v1/search/local.json")
    override suspend fun getPlace(
        @Query("query") query: String,
        @Query("x") lng: String,
        @Query("y") lat: String,
        @Query("raduis") radius: Int,
        @Query("page") page: Int
    ): KakaoMapPlaceResponse
}
