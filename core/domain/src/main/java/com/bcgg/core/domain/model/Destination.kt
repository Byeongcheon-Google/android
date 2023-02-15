package com.bcgg.core.domain.model

import java.time.LocalDateTime

data class Destination(
    val name: String,
    val address: String,
    val lat: Double = Double.NaN,
    val lng: Double = Double.NaN,
    val stayTimeHour: Int,
    val comeTime: LocalDateTime,
    val type: Type
) {
    enum class Type {
        Travel, House, Food
    }
}
