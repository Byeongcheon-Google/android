package com.bcgg.core.security.model

data class JwtToken(
    val accessToken: String,
    val refreshToken: String
)
