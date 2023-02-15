package com.bcgg.core.util.constant

import java.time.LocalTime

object LocalTimeConstants {
    val dayStart: LocalTime = LocalTime.of(0, 0)
    val dayEnd: LocalTime = LocalTime.of(23, 50)

    const val HOURS_PER_DAY = 24

    /**
     * Minutes per hour.
     */
    const val MINUTES_PER_HOUR = 60

    /**
     * Minutes per day.
     */
    const val MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY

    /**
     * Seconds per minute.
     */
    const val SECONDS_PER_MINUTE = 60

    /**
     * Seconds per hour.
     */
    const val SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR

    /**
     * Seconds per day.
     */
    const val SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY

    /**
     * Milliseconds per day.
     */
    const val MILLIS_PER_DAY = SECONDS_PER_DAY * 1000L

    /**
     * Microseconds per day.
     */
    const val MICROS_PER_DAY = SECONDS_PER_DAY * 1000000L

    /**
     * Nanos per millisecond.
     */
    const val NANOS_PER_MILLI = 1000000L

    /**
     * Nanos per second.
     */
    const val NANOS_PER_SECOND = 1000000000L

    /**
     * Nanos per minute.
     */
    const val NANOS_PER_MINUTE = NANOS_PER_SECOND * SECONDS_PER_MINUTE

    /**
     * Nanos per hour.
     */
    const val NANOS_PER_HOUR = NANOS_PER_MINUTE * MINUTES_PER_HOUR

    /**
     * Nanos per day.
     */
    const val NANOS_PER_DAY = NANOS_PER_HOUR * HOURS_PER_DAY

    const val TEN_MINUTES = 10
}
