package com.bcgg.core.data.user

import kotlinx.coroutines.delay

class UserFakeAuthDataSource : UserAuthDataSource {
    override suspend fun getUserId() : String {
        delay(500)
        return "test"
        throw Exception("test")
    }
}