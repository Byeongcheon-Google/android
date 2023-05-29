package com.bcgg.feature.planmanage.state

data class PlanItemUiState(
    val id: Int,
    val title: String,
    val modifiedDateTimeFormatted: String,
    val dateCount: Int,
    val destinations: List<String>
)
