package com.bcgg.core.domain.model

import com.bcgg.core.util.Classification
import java.time.LocalDate

data class Destination(
    val name: String,
    val address: String,
    val lat: Double = Double.NaN,
    val lng: Double = Double.NaN,
    val stayTimeHour: Int,
    val date: LocalDate,
    val classification: Classification
)