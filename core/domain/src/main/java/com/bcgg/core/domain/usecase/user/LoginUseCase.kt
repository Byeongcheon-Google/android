package com.bcgg.core.domain.usecase.user

import com.bcgg.core.domain.repository.UserRepository
import com.bcgg.core.security.util.toSHA256
import com.bcgg.core.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(id: String, password: String): Flow<Result<String>> {
        return userRepository.login(id, password.toSHA256())
    }
}
