package com.bcgg.core.data.source.user

import retrofit2.http.POST

interface UserAuthDataSource {

    @POST("/auth/validation")
    suspend fun getUserId(): String
}