package com.bcgg.core.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("errorCode") val errorCode: String,
    @SerialName("errorMessage") val errorMessage: String,
    @SerialName("httpStatusCode") val httpStatusCode: Int
)
