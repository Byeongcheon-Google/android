package com.bcgg.core.data.source.user

interface UserAuthDataSource {
    suspend fun getUserId(): String
}