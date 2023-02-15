package com.bcgg.feature.planeditor.util
import com.bcgg.feature.planeditor.constant.Constant
import com.bcgg.feature.planeditor.constant.Constant.MIN_STAY_TIME
import java.time.LocalTime

fun calculateStayTimeSliderRightThreshold(maxStayTimeHour: Int) =
    1 - ((maxStayTimeHour - MIN_STAY_TIME) / (Constant.MAX_STAY_TIME - MIN_STAY_TIME).toFloat())

fun calculateMaxStayTime(newTime: LocalTime, availableTime: ClosedRange<LocalTime>) =
    availableTime.endInclusive.hour + MIN_STAY_TIME - newTime.hour
