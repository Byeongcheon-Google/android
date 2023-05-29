package com.bcgg.core.data.source.user

import retrofit2.http.GET
import retrofit2.http.POST

interface UserAuthDataSource {

    @GET("/user")
    suspend fun getUserId(): String
}