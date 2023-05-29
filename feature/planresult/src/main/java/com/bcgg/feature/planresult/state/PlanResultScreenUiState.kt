package com.bcgg.feature.planresult.state

import androidx.compose.runtime.Stable
import com.naver.maps.geometry.LatLngBounds
import java.time.LocalDate

@Stable
data class PlanResultScreenUiState(
    val planName: String = "",
    val dates: List<LocalDate> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
    val planResultItemUiState: List<PlanResultItemUiState> = emptyList(),
    val selectedPlanResultItemUiState: PlanResultItemUiState? = null,
    val entireBounds: LatLngBounds = LatLngBounds.WORLD
)