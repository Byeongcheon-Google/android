package com.bcgg.core.domain.model

import java.time.LocalDateTime

data class Destination(
    val name: String,
    val address: String,
    val katechMapX: String,
    val katechMapY: String,
    val stayTimeHour: Int,
    val comeTime: LocalDateTime,
    val type: Type
) {
    enum class Type {
        Travel, House, Food
    }
}
