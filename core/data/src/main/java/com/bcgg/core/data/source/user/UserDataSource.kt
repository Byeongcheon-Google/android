package com.bcgg.core.data.source.user

import com.bcgg.core.data.response.TokenResponse
import com.bcgg.core.data.request.user.SignInRequest
import com.bcgg.core.data.request.user.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface UserDataSource {

    @POST("/user/login")
    suspend fun login(@Body signInRequest: SignInRequest): String

    @POST("/user")
    suspend fun signUp(@Body signUpRequest: SignUpRequest)

    @POST("/user/check/{username}")
    suspend fun isIdDuplicated(@Query("username") id: String): Boolean
}
