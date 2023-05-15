package com.bcgg.feature.planmanage.state

import androidx.compose.runtime.Stable

@Stable
data class PlanManageScreenUiState(
    val plans: List<PlanItemUiState> = emptyList()
)