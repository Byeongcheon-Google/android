package com.bcgg.core.util.ext

import okhttp3.Request

inline fun Request.newRequest(block: Request.Builder.() -> Request.Builder): Request {
    return this.newBuilder().block().build()
}