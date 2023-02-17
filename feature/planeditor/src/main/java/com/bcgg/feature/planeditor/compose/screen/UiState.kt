package com.bcgg.feature.planeditor.compose.screen

import androidx.paging.PagingData
import com.bcgg.core.domain.model.Destination
import com.bcgg.core.domain.model.MapSearchResult
import com.bcgg.core.domain.model.Schedule
import com.bcgg.core.domain.model.newSchedule
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

data class UiState(
    val expanded: Expanded? = null,
    val search: String = "",
    val isSearching: Boolean = false,
    val searchButtonEnabled: Boolean = false,
    val schedule: Schedule = newSchedule(),
    val mapSearchResult: Flow<PagingData<MapSearchResult>>? = null,
    val selectedSearchPosition: Int = -1,
    val selectedDate: LocalDate = LocalDate.now(),
    val snackbarState: SnackbarState? = null
) {
    val isShowSearchContainer get() = mapSearchResult != null
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
        it.lat == mapSearchResult.lat && it.lng == mapSearchResult.lng
    }
}

fun Iterable<Destination>.find(mapSearchResult: MapSearchResult): Destination? {
    return this.find {
        it.lat == mapSearchResult.lat && it.lng == mapSearchResult.lng
    }
}
