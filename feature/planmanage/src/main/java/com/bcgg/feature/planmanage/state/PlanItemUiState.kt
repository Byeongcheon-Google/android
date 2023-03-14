package com.bcgg.feature.planmanage.state

import java.time.LocalDate
import java.time.LocalDateTime

data class PlanItemUiState(
    val id: Int,
    val title: String,
    val modifiedDateTime: LocalDateTime,
    val date: ClosedRange<LocalDate>,
    val destinations: List<String>
)
