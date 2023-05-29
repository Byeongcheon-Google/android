package com.bcgg.core.data.model.schedule

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SchedulePerDate(
    @SerialName("id") val id: Int,
    @SerialName("scheduleId") val scheduleId: Int,
    @SerialName("date") val date: LocalDate,
    @SerialName("startTime") val startTime: LocalTime,
    @SerialName("endHopeTime") val endHopeTime: LocalTime,
    @SerialName("mealTimes") val mealTimes: List<LocalTime>,
    @SerialName("startPlaceId") val startPlaceId: Int?,
    @SerialName("endPlaceId") val endPlaceId: Int?,
)