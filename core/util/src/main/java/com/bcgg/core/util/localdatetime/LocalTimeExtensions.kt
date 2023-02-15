package com.bcgg.core.util.localdatetime

import com.bcgg.core.util.constant.LocalTimeConstants.TEN_MINUTES
import java.time.LocalTime

val LocalTime.floorTenMinutes get() = this.minute / TEN_MINUTES * TEN_MINUTES
