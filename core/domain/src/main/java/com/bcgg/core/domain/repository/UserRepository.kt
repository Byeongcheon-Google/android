package com.bcgg.core.domain.repository

import com.bcgg.core.data.user.UserDataSource
import com.bcgg.core.domain.mapper.user.toJwtToken
import com.bcgg.core.security.model.JwtToken
import com.bcgg.core.security.source.JwtTokenSecuredLocalDataSource
import com.bcgg.core.security.util.toSHA256
import com.bcgg.core.util.map
import com.bcgg.core.util.onSuccess
import com.bcgg.core.util.toFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val jwtTokenSecuredLocalDataSource: JwtTokenSecuredLocalDataSource,
    private val userDataSource: UserDataSource
) {
    fun login(email: String, password: String): Flow<com.bcgg.core.util.Result<JwtToken>> = flow {
        try {
            val jwtToken = userDataSource.login(email, password.toSHA256()).map { it.toJwtToken() }

            emit(
                jwtToken.onSuccess {
                    jwtTokenSecuredLocalDataSource.saveJwtToken(it)
                }
            )
        } catch (e: Throwable) {
            emit(e.toFailure())
        }
    }
}
