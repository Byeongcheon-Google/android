package com.bcgg.core.data.user

import com.bcgg.core.data.response.TokenResponse

interface UserDataSource {
    suspend fun login(email: String, password: String): com.bcgg.core.util.Result<TokenResponse>
}
