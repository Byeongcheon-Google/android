package com.bcgg.core.domain.util

import com.bcgg.core.util.Classification

fun String.toClassification() = when(this) {
    "FD6" -> Classification.Food
    "AD5" -> Classification.House
    else -> Classification.Travel
}