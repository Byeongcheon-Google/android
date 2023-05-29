package com.bcgg.core.domain.model.schedule

import com.bcgg.core.util.Classification
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class ScheduleDetail(
    val id: Int,
    val title: String,
    val ownerId: String,
    val schedulePerDates: List<PerDate>
) {
    data class PerDate(
        val id: Int,
        val date: LocalDate,
        val startTime: LocalTime,
        val endHopeTime: LocalTime,
        val mealTimes: List<LocalTime>,
        val startPlaceId: Int?,
        val endPlaceId: Int?,
        val places: List<Place>
    ) {
        data class Place (
            val id: Int,
            val scheduleId: Int,
            val date: LocalDate,
            val kakaoPlaceId: String,
            val name: String,
            val address: String,
            val lat: Double,
            val lng: Double,
            val classification: Classification,
            val stayTimeHour: Int
        )
    }
}