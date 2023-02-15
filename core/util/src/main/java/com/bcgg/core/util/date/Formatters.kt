package com.bcgg.core.util.date

import java.time.format.DateTimeFormatter

val weekdayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("E")
val monthDayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("M/dd")
val hourMinuteFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
