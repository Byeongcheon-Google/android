package com.bcgg.feature.planeditor.util

import com.bcgg.core.domain.model.Destination
import com.bcgg.core.util.constant.LocalTimeConstants
import com.bcgg.feature.planeditor.constant.Constant.DAY_HOURS_THRESHOLD
import com.bcgg.feature.planeditor.constant.Constant.MIN_STAY_TIME
import java.time.LocalTime

fun calculateDestinationAvailableTime(
    beforeDestination: Destination?,
    afterDestination: Destination?
): ClosedRange<LocalTime> {
    val startTime = if (beforeDestination == null) {
        LocalTimeConstants.dayStart
    } else {
        if (beforeDestination.comeTime.hour + beforeDestination.stayTimeHour > DAY_HOURS_THRESHOLD) {
            LocalTimeConstants.dayEnd
        } else {
            beforeDestination.comeTime.toLocalTime()
                .plusHours(beforeDestination.stayTimeHour.toLong())
        }
    }
    val endTime =
        afterDestination?.comeTime?.toLocalTime()?.minusHours(MIN_STAY_TIME.toLong()) ?: LocalTimeConstants.dayEnd

    return startTime..endTime
}
