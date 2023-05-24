package com.bcgg.feature.planeditor.compose.screen

import androidx.paging.PagingData
import com.bcgg.core.domain.model.Destination
import com.bcgg.core.domain.model.editor.map.PlaceSearchResult
import com.bcgg.core.domain.model.Schedule
import com.bcgg.core.domain.model.newSchedule
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

data class UiState(
    val schedule: Schedule = newSchedule(),
    val placeSearchResult: Flow<PagingData<PlaceSearchResult>>? = null,
    val selectedSearchPosition: Int = -1,
    val selectedDate: LocalDate = LocalDate.now(),
    val snackbarState: SnackbarState? = null
) {
    val isShowSearchContainer get() = placeSearchResult != null
    val hasNoSearchResult get() = placeSearchResult == null

    enum class Expanded {
        ScheduleEdit, SearchResult
    }

    enum class SnackbarState {
        NotAvailableTime
    }
}

fun Iterable<Destination>.contains(placeSearchResult: PlaceSearchResult): Boolean {
    return this.any {
        it.lat == placeSearchResult.lat && it.lng == placeSearchResult.lng
    }
}

fun Iterable<Destination>.find(placeSearchResult: PlaceSearchResult): Destination? {
    return this.find {
        it.lat == placeSearchResult.lat && it.lng == placeSearchResult.lng
    }
}
