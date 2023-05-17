package com.bcgg.core.data.user

interface UserAuthDataSource {
    suspend fun checkTokenIsValid(accessToken: String)
}