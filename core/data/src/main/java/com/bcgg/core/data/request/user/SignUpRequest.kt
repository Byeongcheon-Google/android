package com.bcgg.core.data.request.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("id") val id: String,
    @SerialName("password") val password: String,
)
