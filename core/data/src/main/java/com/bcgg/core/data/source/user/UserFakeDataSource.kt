package com.bcgg.core.data.source.user

import com.bcgg.core.data.request.user.SignInRequest
import com.bcgg.core.data.response.TokenResponse
import com.bcgg.core.data.request.user.SignUpRequest
import kotlinx.coroutines.delay

class UserFakeDataSource : UserDataSource {
    override suspend fun login(signInRequest: SignInRequest): String {
        delay(FAKE_HTTP_DELAY)
        if(signInRequest.username == "fail") throw RuntimeException("아이디를 확인해 주세요")
        if(signInRequest.password == "failfail") throw RuntimeException("비밀번호를 확인해 주세요")

        return "fake access token"
    }

    override suspend fun signUp(signUpRequest: SignUpRequest) {
        delay(FAKE_HTTP_DELAY)
        if (signUpRequest.username == "fail") throw RuntimeException("회원가입에 실패하였습니다.")
    }

    override suspend fun isIdDuplicated(id: String): String {
        delay(FAKE_HTTP_DELAY)
        if(id == "dup") throw IllegalStateException("Id is duplicated")
        return "사용가능한 ID 입니다."
    }

    companion object {
        const val FAKE_HTTP_DELAY = 400L
    }
}
