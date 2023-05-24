package com.bcgg.core.domain.model.editor.map

import androidx.compose.runtime.Stable
import com.bcgg.core.domain.model.Classification

@Stable
data class PlaceSearchResult(
    val id: String = "",
    val name: String,
    val address: String,
    val lat: Double = Double.NaN,
    val lng: Double = Double.NaN,
)

@Stable
data class PlaceSearchResultWithId(
    val userId: String,
    val placeSearchResult: PlaceSearchResult,
    val stayTimeHour: Int = 1,
    val classification: Classification = Classification.Travel
)
