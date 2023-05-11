package com.bcgg.feature.planresult.state

import com.bcgg.core.domain.model.Classification
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import java.time.LocalDate
import java.time.LocalTime

sealed class PlanResultItemUiState(
    open val date: LocalDate,
    open val timeRange: ClosedRange<LocalTime>
) {
    data class Place (
        val number: Int,
        val name: String,
        override val date: LocalDate,
        override val timeRange: ClosedRange<LocalTime>,
        val point: LatLng,
        val classification: Classification,
    ) : PlanResultItemUiState(date, timeRange)

    data class Move (
        val distanceDescription: String,
        override val date: LocalDate,
        override val timeRange: ClosedRange<LocalTime>,
        val bound: LatLngBounds,
        val points: List<LatLng>,
        val startPlace: LatLng,
        val endPlace: LatLng
    ) : PlanResultItemUiState(date, timeRange)

}
