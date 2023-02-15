package com.bcgg.core.domain.model

data class MapSearchResult(
    val name: String,
    val address: String,
    val lat: Double = Double.NaN,
    val lng: Double = Double.NaN
)
