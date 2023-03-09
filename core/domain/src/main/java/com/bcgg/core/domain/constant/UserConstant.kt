package com.bcgg.core.domain.constant

object UserConstant {
    val EMAIL_REGEX: Regex = """^[\w-.]+@([\w-]+\.)+[\w-]+${'$'}""".toRegex()
    const val PASSWORD_MIN_LENGTH = 8
}
