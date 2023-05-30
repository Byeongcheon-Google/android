package com.bcgg.feature.planeditor.compose.state

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.bcgg.core.data.model.Location
import com.bcgg.core.util.PlaceSearchResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Stable
data class PlanEditorMapUiState(
    val placeSearchResult: Flow<PagingData<PlaceSearchResult>>? = null,
    val placeSearchResultAi: List<PlaceSearchResult> = emptyList(),
    val expanded: Boolean = true,
    val isAiSearching: Boolean = false,
    val search: String = "",
    val selectedSearchResult: PlaceSearchResult? = null,
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
