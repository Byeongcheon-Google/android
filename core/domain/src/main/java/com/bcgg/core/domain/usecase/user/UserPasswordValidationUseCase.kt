package com.bcgg.core.domain.usecase.user

import com.bcgg.core.domain.constant.UserConstant.PASSWORD_MIN_LENGTH
import com.bcgg.core.domain.model.state.UserPasswordValidationState
import javax.inject.Inject

class UserPasswordValidationUseCase @Inject constructor() {
    operator fun invoke(password: String): UserPasswordValidationState {
        if(password.length < PASSWORD_MIN_LENGTH) return UserPasswordValidationState.TooShort

        return UserPasswordValidationState.OK
    }
}
