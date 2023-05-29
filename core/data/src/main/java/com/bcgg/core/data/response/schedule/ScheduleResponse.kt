package com.bcgg.core.data.response.schedule

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleResponse(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("modifiedDateTime") val modifiedDateTime: LocalDateTime,
    @SerialName("dateCount") val dateCount: Int,
    @SerialName("destinations") val destinations: List<String>,
    @SerialName("collaboratorUserIds") val collaboratorUserIds: List<String>
)