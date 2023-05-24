package com.bcgg.core.domain.model.editor

import java.time.LocalDate
import java.time.LocalTime

data class PathFinderInput(
    val date: LocalDate = LocalDate.now(),
    val startTime: LocalTime = LocalTime.of(9, 0), //시작 시간
    val endHopeTime: LocalTime = LocalTime.of(18, 0), //종료 희망 시간
    val mealTimes: List<LocalTime> = emptyList(), //식사 희망 시간(정렬되어 있어야 함)
    val startPoint: Point? = null, // 시작 지점
    val endPoint: Point? = null, // 끝 지점
    val points: Collection<Point> = emptyList()
)