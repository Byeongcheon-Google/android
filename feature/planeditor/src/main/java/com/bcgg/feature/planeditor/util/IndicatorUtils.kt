package com.bcgg.feature.planeditor.util

import com.bcgg.feature.planeditor.compose.indicator.IndicatorType

fun positionToIndicatorType(position: Int, listSize: Int) = when {
    listSize == 1 -> IndicatorType.One
    position == 0 -> IndicatorType.Header
    position == listSize - 1 -> IndicatorType.Footer
    else -> IndicatorType.Normal
}
