package com.bcgg.core.data.user

interface UserAuthDataSource {
    suspend fun getUserId(): String
}