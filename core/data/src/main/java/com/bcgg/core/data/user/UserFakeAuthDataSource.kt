package com.bcgg.core.data.user

import kotlinx.coroutines.delay

class UserFakeAuthDataSource : UserAuthDataSource {
    override suspend fun checkTokenIsValid(accessToken: String) {
        delay(500)
        return
        throw Exception("test")
    }
}