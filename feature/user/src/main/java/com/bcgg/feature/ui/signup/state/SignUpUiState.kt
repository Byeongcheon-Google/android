package com.bcgg.feature.ui.signup.state

import com.bcgg.core.domain.model.state.UserPasswordValidationState

data class SignUpUiState(
    val id: String = "",
    val isIdDuplicated: Boolean? = null,
    val password: String = "",
    val passwordConfirm: String = "",
    val passwordState: UserPasswordValidationState = UserPasswordValidationState.NoPassword
) {
    val isSignUpEnabled
        get() = id.isNotBlank() &&
                password.isNotBlank() &&
                isPasswordMatch &&
                isIdDuplicated == false

    val isIdDuplicateCheckEnabled = id.isNotBlank() && isIdDuplicated == null

    val isPasswordMatch = password == passwordConfirm
}
