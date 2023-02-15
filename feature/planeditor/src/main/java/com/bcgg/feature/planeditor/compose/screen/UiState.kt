package com.bcgg.feature.planeditor.compose.screen

import com.bcgg.core.domain.model.MapSearchResult
import com.bcgg.core.domain.model.Schedule
import com.bcgg.core.domain.model.newSchedule
import java.time.LocalDate

data class UiState(
    val expanded: Expanded? = null,
    val search: String = "",
    val schedule: Schedule = newSchedule(),
    val mapSearchResult: List<MapSearchResult>? = null,
    val selectedDate: LocalDate = LocalDate.now()
) {
    val isShowSearchContainer get() = search.isNotBlank()
    val hasNoSearchResult get() = mapSearchResult == null

    enum class Expanded {
        ScheduleEdit, SearchResult
    }
}
