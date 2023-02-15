package com.bcgg.core.util.ext

import kotlin.math.pow

const val POW = 0.1

fun <T : Comparable<T>> T.mediateOutOfRangedValue(range: ClosedRange<T>): T {
    return if (this < range.start) range.start
    else if (this > range.endInclusive) range.endInclusive
    else this
}

fun Float.floor(numberOfDigits: Int = 0): Float {
    return ((this / POW.pow(numberOfDigits)).toInt() * POW.pow(numberOfDigits)).toFloat()
}
