package com.bcgg.core.security.source

import com.bcgg.core.security.model.JwtToken
import kotlinx.coroutines.flow.Flow

interface JwtTokenSecuredLocalDataSource {
    fun getJwtToken(): Flow<JwtToken>
    suspend fun saveJwtToken(token: JwtToken)
}