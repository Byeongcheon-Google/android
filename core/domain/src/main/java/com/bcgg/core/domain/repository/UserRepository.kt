package com.bcgg.core.domain.repository

import com.bcgg.core.data.user.UserAuthDataSource
import com.bcgg.core.data.user.UserDataSource
import com.bcgg.core.domain.mapper.user.toJwtToken
import com.bcgg.core.security.model.JwtToken
import com.bcgg.core.security.source.JwtTokenSecuredLocalDataSource
import com.bcgg.core.security.source.JwtTokenSecuredLocalDataSourceImpl
import com.bcgg.core.util.Result
import com.bcgg.core.util.toFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val jwtTokenSecuredLocalDataSource: JwtTokenSecuredLocalDataSource,
    private val userDataSource: UserDataSource,
    private val userAuthDataSource: UserAuthDataSource
) {
    fun login(id: String, passwordHashed: String): Flow<Result<JwtToken>> = flow {
        try {
            val jwtToken = userDataSource.login(id, passwordHashed).toJwtToken()
            jwtTokenSecuredLocalDataSource.saveJwtToken(jwtToken)
            emit(Result.Success(jwtToken))
        } catch (e: Throwable) {
            emit(e.toFailure())
        }
    }

    fun signUp(id: String, passwordHashed: String): Flow<Result<Unit>> = flow {
        try {
            userDataSource.signUp(id, passwordHashed)
            emit(Result.Success(Unit))
        } catch (t: Throwable) {
            emit(t.toFailure())
        }
    }

    fun isIdDuplicated(id: String): Flow<Result<Boolean>> = flow {
        try {
            emit(Result.Success(userDataSource.isIdDuplicated(id)))
        } catch (t: Throwable) {
            emit(t.toFailure())
        }
    }

    fun checkToken(): Flow<Result<Unit>> = jwtTokenSecuredLocalDataSource.getJwtToken().map {
        try {
            Result.Success(userAuthDataSource.checkTokenIsValid(it.accessToken))
        } catch (t: Throwable) {
            t.toFailure()
        }
    }
}
