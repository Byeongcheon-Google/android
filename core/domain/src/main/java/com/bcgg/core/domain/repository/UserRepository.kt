package com.bcgg.core.domain.repository

import com.bcgg.core.data.request.user.SignInRequest
import com.bcgg.core.data.request.user.SignUpRequest
import com.bcgg.core.data.source.user.UserAuthDataSource
import com.bcgg.core.data.source.user.UserDataSource
import com.bcgg.core.data.util.toErrorMessage
import com.bcgg.core.domain.model.User
import com.bcgg.core.security.source.JwtTokenSecuredLocalDataSource
import com.bcgg.core.util.Result
import com.bcgg.core.util.toFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val jwtTokenSecuredLocalDataSource: JwtTokenSecuredLocalDataSource,
    private val userDataSource: UserDataSource,
    private val userAuthDataSource: UserAuthDataSource
) {
    fun login(id: String, passwordHashed: String): Flow<Result<String>> = flow {
        try {
            val accessToken = userDataSource.login(
                SignInRequest(
                    username = id,
                    password = passwordHashed
                )
            )
            jwtTokenSecuredLocalDataSource.saveAccessToken(accessToken)
            emit(Result.Success(accessToken))
        } catch (e: HttpException) {
            if(e.code() == 400) {
                emit(Result.Failure("아이디 또는 비밀번호가 일치하지 않습니다."))
            } else {
                emit(e.toErrorMessage())
            }
        } catch (e: Throwable) {
            emit(e.toFailure())
        }
    }

    fun getUser(): Flow<Result<User>> = flow {
        emit(
            try {
                Result.Success(
                    User(
                        userId = userAuthDataSource.getUserId()
                    )
                )
            } catch (e: HttpException) {
                e.toErrorMessage()
            } catch (t: Throwable) {
                t.toFailure()
            }
        )
    }

    fun signUp(id: String, passwordHashed: String): Flow<Result<Unit>> = flow {
        try {
            userDataSource.signUp(
                SignUpRequest(
                    username = id,
                    password = passwordHashed,
                    roles = listOf("ROLE_USER")
                )
            )
            emit(Result.Success(Unit))
        } catch (e: HttpException) {
            emit(e.toErrorMessage())
        } catch (t: Throwable) {
            emit(t.toFailure())
        }
    }

    fun isIdDuplicated(id: String): Flow<Result<Boolean>> = flow {
        try {
            userDataSource.isIdDuplicated(id)
            emit(Result.Success(false))
        } catch (e: HttpException) {
            emit(Result.Success(true))
        } catch (t: Throwable) {
            emit(t.toFailure())
        }
    }
}
