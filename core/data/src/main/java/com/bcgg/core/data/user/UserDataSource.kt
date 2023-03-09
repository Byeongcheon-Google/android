package com.bcgg.core.data.user

import com.bcgg.core.data.response.TokenResponse

interface UserDataSource {
    suspend fun login(id: String, password: String): TokenResponse
    suspend fun signUp(id: String, password: String)
    suspend fun isIdDuplicated(id: String): Boolean
}
