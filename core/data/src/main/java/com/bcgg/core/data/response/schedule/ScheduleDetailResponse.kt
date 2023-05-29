package com.bcgg.core.data.response.schedule

import com.bcgg.core.data.model.schedule.Schedule
import com.bcgg.core.data.model.schedule.SchedulePerDate
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleDetailResponse(
    val schedule: Schedule,
    val schedulePerDates: List<SchedulePerDate>
)