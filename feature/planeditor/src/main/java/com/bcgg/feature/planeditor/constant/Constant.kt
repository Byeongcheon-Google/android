package com.bcgg.feature.planeditor.constant

object Constant {
    const val MIN_STAY_TIME = 1
    const val MAX_STAY_TIME = 12

    const val HOUR_DIVIDER = 360 / 12f
    const val MINUTE_DIVIDER = 360 / 12 / 60f

    const val DAY_HOURS_THRESHOLD = 24

    const val TIME_SLIDER_END_TIME = 23f + 5 / 6f

    const val VERTICAL_LINE_TICK_START = 0
    const val SLIDER_MINUTE_FLOOR = -1

    const val SEARCH_DEBOUNCE = 100L
}
