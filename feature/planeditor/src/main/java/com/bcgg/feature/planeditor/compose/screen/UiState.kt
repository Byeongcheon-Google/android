package com.bcgg.feature.planeditor.compose.screen

import com.bcgg.core.domain.model.Destination
import com.bcgg.core.domain.model.MapSearchResult
import com.bcgg.core.domain.model.Schedule
import com.bcgg.core.domain.model.newSchedule
import java.time.LocalDate

data class UiState(
    val expanded: Expanded? = null,
    val search: String = "",
    val isSearching: Boolean = false,
    val schedule: Schedule = newSchedule(),
    val mapSearchResult: List<MapSearchResult>? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val snackbarState: SnackbarState? = null
) {
    val isShowSearchContainer get() = search.isNotBlank()
    val hasNoSearchResult get() = mapSearchResult == null

    enum class Expanded {
        ScheduleEdit, SearchResult
    }

    enum class SnackbarState {
        NotAvailableTime
    }
}

fun Iterable<Destination>.contains(mapSearchResult: MapSearchResult): Boolean {
    return this.any {
        it.katechMapX == mapSearchResult.katechMapX &&
            it.katechMapY == mapSearchResult.katechMapY
    }
}

fun Iterable<Destination>.find(mapSearchResult: MapSearchResult): Destination? {
    return this.find {
        it.katechMapX == mapSearchResult.katechMapX &&
            it.katechMapY == mapSearchResult.katechMapY
    }
}
