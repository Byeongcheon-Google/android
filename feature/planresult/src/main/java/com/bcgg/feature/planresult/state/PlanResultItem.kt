package com.bcgg.feature.planresult.state

import com.bcgg.core.domain.model.Location
import com.naver.maps.geometry.LatLng
import java.time.LocalDate
import java.time.LocalTime

sealed class PlanResultItem(
    open val date: LocalDate,
    open val timeRange: ClosedRange<LocalTime>
) {
    data class Place (
        val name: String,
        override val date: LocalDate,
        override val timeRange: ClosedRange<LocalTime>,
        val point: LatLng,
    ) : PlanResultItem(date, timeRange)

    data class Move (
        val distanceDescription: String,
        override val date: LocalDate,
        override val timeRange: ClosedRange<LocalTime>,
        val points: List<LatLng>
    ) : PlanResultItem(date, timeRange)


}
