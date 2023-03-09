package com.bcgg.core.domain.usecase.user

import com.bcgg.core.domain.constant.UserConstant.PASSWORD_MIN_LENGTH
import javax.inject.Inject

class UserPasswordValidationUseCase @Inject constructor() {
    operator fun invoke(password: String): Boolean {
        return password.length >= PASSWORD_MIN_LENGTH
    }
}
