package com.bcgg.core.data.user

import com.bcgg.core.data.response.TokenResponse
import com.bcgg.core.util.Result
import kotlinx.coroutines.delay

class UserFakeDataSource : UserDataSource {
    override suspend fun login(email: String, password: String): Result<TokenResponse> {
        return Result.Success(
            TokenResponse(
                accessToken = "fake access token",
                refreshToken = "fake refresh token"
            )
        )
    }
}
