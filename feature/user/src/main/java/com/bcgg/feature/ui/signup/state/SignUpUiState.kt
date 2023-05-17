package com.bcgg.feature.ui.signup.state

data class SignUpUiState(
    val id: String = "",
    val isIdDuplicated: Boolean? = null,
    val password: String = "",
    val passwordConfirm: String = ""
) {
    val isSignUpEnabled
        get() = id.isNotBlank() &&
                password.isNotBlank() &&
                isPasswordMatch &&
                isIdDuplicated == false

    val isIdDuplicateCheckEnabled = id.isNotBlank() && isIdDuplicated == null

    val isPasswordMatch = password == passwordConfirm
}
