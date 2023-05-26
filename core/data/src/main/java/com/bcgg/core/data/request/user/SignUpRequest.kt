package com.bcgg.core.data.request.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("username") val username: String,
    @SerialName("password") val password: String,
    @SerialName("roles") val roles: List<String>
)
