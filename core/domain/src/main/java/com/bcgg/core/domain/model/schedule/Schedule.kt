package com.bcgg.core.domain.model.schedule

import kotlinx.datetime.LocalDateTime

data class Schedule(
    val id: Int,
    val title: String,
    val modifiedDateTime: LocalDateTime,
    val dateCount: Int,
    val destinations: List<String>,
    val collaboratorUserIds: List<String>
)