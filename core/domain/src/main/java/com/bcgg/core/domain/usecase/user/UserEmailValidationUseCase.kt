package com.bcgg.core.domain.usecase.user

import com.bcgg.core.domain.constant.UserConstant
import javax.inject.Inject

class UserEmailValidationUseCase @Inject constructor() {
    operator fun invoke(email: String): Boolean {
        return email.matches(UserConstant.EMAIL_REGEX)
    }
}
