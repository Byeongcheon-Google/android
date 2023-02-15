package com.bcgg.core.domain.model

data class MapSearchResult(
    val name: String,
    val distance: String,
    val address: String,
    val lat: Double,
    val lng: Double
)
