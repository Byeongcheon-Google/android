package com.bcgg.core.util.ext

import com.bcgg.core.util.constant.LocalTimeConstants.TEN_MINUTES
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

val LocalTime.floorTenMinutes get() = this.minute / TEN_MINUTES * TEN_MINUTES
val ClosedRange<LocalTime>.duration get() = Duration.between(this.start, this.endInclusive)

val schedulesDateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)