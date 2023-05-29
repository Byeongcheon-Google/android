package com.bcgg.core.data.request.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    @SerialName("id") val username: String,
    @SerialName("password") val password: String,
)
