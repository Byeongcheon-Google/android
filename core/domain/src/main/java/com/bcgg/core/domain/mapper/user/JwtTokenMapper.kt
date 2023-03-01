package com.bcgg.core.domain.mapper.user

import com.bcgg.core.data.response.TokenResponse
import com.bcgg.core.security.model.JwtToken

fun TokenResponse.toJwtToken() = JwtToken(
    accessToken, refreshToken
)
