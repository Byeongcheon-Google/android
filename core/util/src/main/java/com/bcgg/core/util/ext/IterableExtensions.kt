package com.bcgg.core.util.ext

fun <T> Iterable<T>.removed(remove: T) = this.filter { it != remove }
