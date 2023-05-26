package com.bcgg.core.security.util

import okhttp3.Request

fun Request.Builder.putAccessToken(
    accessToken: String,
    name: String = "X-AUTH-TOKEN",
    prefix: String = "Bearer "
): Request.Builder {
    return addHeader(name, "$prefix$accessToken")
}