package com.bcgg.core.domain.usecase.user

import com.bcgg.core.domain.repository.UserRepository
import com.bcgg.core.security.util.toSHA256
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        id: String,
        password: String,
        passwordConfirm: String
    ): Flow<com.bcgg.core.util.Result<Unit>> {
        if (password != passwordConfirm) return flow {
            emit(
                com.bcgg.core.util.Result.Failure(
                    "비밀번호가 일치하지 않습니다."
                )
            )
        }
        return userRepository.signUp(id, password.toSHA256())
    }
}
