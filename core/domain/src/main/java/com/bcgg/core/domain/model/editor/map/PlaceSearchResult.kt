package com.bcgg.core.domain.model.editor.map

import androidx.compose.runtime.Stable
import com.bcgg.core.domain.model.Classification
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class PlaceSearchResult @OptIn(ExperimentalSerializationApi::class) constructor(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("address") val address: String,
    @EncodeDefault @SerialName("lat") val lat: Double = 0.0,
    @EncodeDefault @SerialName("lng") val lng: Double = 0.0,
    @EncodeDefault @SerialName("classification") val classification: Classification = Classification.Travel
)

@Stable
@Serializable
data class PlaceSearchResultWithId @OptIn(ExperimentalSerializationApi::class) constructor(
    @SerialName("userId") val userId: String,
    @SerialName("placeSearchResult") val placeSearchResult: PlaceSearchResult,
    @EncodeDefault @SerialName("stayTimeHour") val stayTimeHour: Int = 1
)
