package com.bcgg.feature.planeditor.util

import com.bcgg.core.util.constant.LocalTimeConstants.MINUTES_PER_HOUR
import com.bcgg.feature.planeditor.constant.Constant
import java.time.LocalTime

fun calculateComeTimeSliderLeftThreshold(availableTime: ClosedRange<LocalTime>) =
    (availableTime.start.hour + (availableTime.start.minute / MINUTES_PER_HOUR)) / Constant.TIME_SLIDER_END_TIME

fun calculateComeTimeSliderRightThreshold(availableTime: ClosedRange<LocalTime>) =
    1 - (
        (
            availableTime.endInclusive.hour +
                (availableTime.endInclusive.minute / MINUTES_PER_HOUR)
            ) /
            Constant.TIME_SLIDER_END_TIME
        )
