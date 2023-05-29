package com.bcgg.core.data.source.schedule

import com.bcgg.core.data.model.schedule.Schedule
import com.bcgg.core.data.response.schedule.ScheduleDetailResponse
import com.bcgg.core.data.response.schedule.ScheduleResponse
import com.bcgg.core.data.response.schedule.SchedulePerDateResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleAuthDataSource {
    @GET("/schedule")
    suspend fun listSchedules(): List<ScheduleResponse>

    @GET("/schedule/{scheduleId}")
    suspend fun getScheduleDetail(@Query("scheduleId") scheduleId: Int): ScheduleDetailResponse

    @GET("/schedule/{scheduleId}/{date}")
    suspend fun getPerDateScheduleDetail(
        @Path("scheduleId") scheduleId: Int,
        @Path("date") date: String
    ): SchedulePerDateResponse

    @POST("/schedule")
    suspend fun addSchedule(@Body schedule: Schedule): Int

    @POST("/schedule/{scheduleId}/collaborators/{userId}")
    suspend fun addCollaborator(
        @Path("scheduleId") scheduleId: Int,
        @Path("userId") userId: String
    )
}