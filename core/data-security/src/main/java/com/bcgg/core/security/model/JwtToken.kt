package com.bcgg.core.security.model

import kotlinx.serialization.Serializable

@Serializable
data class JwtToken(
    val accessToken: String,
    val refreshToken: String
)
