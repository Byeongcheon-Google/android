package com.bcgg.core.data.source.find

import com.bcgg.core.util.PathFinderResult
import com.bcgg.core.util.PlanResultResponse
import kotlinx.datetime.LocalDate
import retrofit2.http.GET
import retrofit2.http.Path

interface PathFinderDataSource {
    @GET("/find/{scheduleId}")
    suspend fun findPath(@Path("scheduleId") scheduleId: Int) : PlanResultResponse
}