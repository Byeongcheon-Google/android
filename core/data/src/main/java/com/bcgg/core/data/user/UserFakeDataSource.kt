package com.bcgg.core.data.user

import com.bcgg.core.data.response.TokenResponse
import kotlinx.coroutines.delay

class UserFakeDataSource : UserDataSource {
    override suspend fun login(id: String, password: String): TokenResponse {
        delay(FAKE_HTTP_DELAY)
        if(id == "fail") throw RuntimeException("아이디를 확인해 주세요")
        if(password == "failfail") throw RuntimeException("비밀번호를 확인해 주세요")

        return TokenResponse(
            accessToken = "fake access token",
            refreshToken = "fake refresh token"
        )
    }

    override suspend fun signUp(id: String, password: String) {
        delay(FAKE_HTTP_DELAY)
        if (id == "fail") throw RuntimeException("회원가입에 실패하였습니다.")
    }

    override suspend fun isIdDuplicated(id: String): Boolean {
        delay(FAKE_HTTP_DELAY)
        return id == "dup"
    }

    companion object {
        const val FAKE_HTTP_DELAY = 400L
    }
}
