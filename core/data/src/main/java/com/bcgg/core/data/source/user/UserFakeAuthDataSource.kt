package com.bcgg.core.data.source.user

import kotlinx.coroutines.delay

class UserFakeAuthDataSource : UserAuthDataSource {
    override suspend fun getUserId() : String {
        delay(500)
        return "ysm2"
        throw Exception("test")
    }
}