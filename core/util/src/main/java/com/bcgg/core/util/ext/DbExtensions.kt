package com.bcgg.core.util.ext

import kotlinx.datetime.*
import java.time.ZoneId
import java.time.format.DateTimeFormatter

val dbDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
val dbTimeFormatter: DateTimeFormatter  = DateTimeFormatter.ofPattern("HH:mm")

val dateNow = java.time.LocalDate.now(ZoneId.of("Asia/Seoul")).toKotlinLocalDate()
val dateTimeNow = Clock.System.now().toLocalDateTime(TimeZone.of("Asia/Seoul"))

fun LocalDate.toDbString() = dbDateFormatter.format(toJavaLocalDate())
fun LocalTime.toDbString() = dbTimeFormatter.format(toJavaLocalTime())
fun String.appendSecondAndToLocalTime() = "${this}:00".toLocalTime()