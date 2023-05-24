package com.bcgg.feature.planeditor.compose.state

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.bcgg.core.domain.model.Location
import com.bcgg.core.domain.model.User
import com.bcgg.core.domain.model.editor.map.PlaceSearchResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Stable
data class PlanEditorMapUiState(
    val placeSearchResult: Flow<PagingData<PlaceSearchResult>>? = null,
    val expanded: Boolean = true,
    val isSearching: Boolean = false,
    val search: String = "",
    val selectedSearchPosition: Int = -1,
    val otherMapPositions: Map<String, Location> = emptyMap(),
    val otherShowingMarkers: List<OtherShowingMarker> = emptyList(),
    val addedSearchResults: List<PlaceSearchResult> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
) {
    data class OtherMapPosition(
        val userId: String,
        val lat: Double,
        val lon: Double
    )

    data class OtherShowingMarker(
        val userId: String,
        val lat: Double,
        val lon: Double
    )
}
