package com.bcgg.core.util.date

import java.time.format.DateTimeFormatter

val weekdayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("E")
val monthDayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("M/d")
val hourMinuteFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")