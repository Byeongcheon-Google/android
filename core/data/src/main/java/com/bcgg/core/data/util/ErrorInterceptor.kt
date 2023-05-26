package com.bcgg.core.data.util

import com.bcgg.core.data.response.ErrorResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        return try {
            val errorResponse = Json.decodeFromString<ErrorResponse>(response.body?.string() ?: "")

            response.newBuilder().code(errorResponse.httpStatusCode).build()
        } catch (e: Exception) {
            response
        }
    }
}