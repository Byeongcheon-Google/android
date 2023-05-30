package com.bcgg.core.data.source.recommend

import com.bcgg.core.data.response.RecommendResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RecommendDataSource {

    @POST("/recommend_api/dis/places")
    suspend fun getRecommendPlacesWithDescription(@Body body: RequestBody): List<RecommendResponse>

    @POST("/recommend_api/cbv/places")
    suspend fun getRecommendPlacesWithAddress(@Body body: RequestBody): List<RecommendResponse>
}