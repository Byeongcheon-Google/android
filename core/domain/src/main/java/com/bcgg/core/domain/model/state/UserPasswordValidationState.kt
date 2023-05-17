package com.bcgg.core.domain.model.state

sealed class UserPasswordValidationState {
    object OK: UserPasswordValidationState()
    object TooShort: UserPasswordValidationState()
    object NoPassword: UserPasswordValidationState()
}
