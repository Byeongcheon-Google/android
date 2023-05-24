package com.bcgg.feature.planeditor.compose.state

import androidx.compose.runtime.Stable
import com.bcgg.core.domain.model.User
import com.bcgg.core.domain.model.editor.map.PlaceSearchResultWithId
import java.time.LocalDate
import java.time.LocalTime

@Stable
data class OptionsUiState(
    val name: String = "새 여행 계획",
    val selectedDate: LocalDate = LocalDate.now(),
    val activeUsers: List<User> = emptyList()
)

@Stable
data class PlanEditorOptionsUiStatePerDate(
    val searchResultMaps: List<PlaceSearchResultWithId> = listOf(),
    val startTime: LocalTime = LocalTime.of(9, 0),
    val endHopeTime: LocalTime = LocalTime.of(18, 0),
    val mealTimes: List<LocalTime> = listOf(),
    val startPlaceSearchResult: PlaceSearchResultWithId? = null,
    val endPlaceSearchResult: PlaceSearchResultWithId? = null
)

val initialPlanEditorOptionsUiStatePerDate = PlanEditorOptionsUiStatePerDate()