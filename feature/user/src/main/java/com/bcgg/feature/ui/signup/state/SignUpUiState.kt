package com.bcgg.feature.ui.signup.state

data class SignUpUiState(
    val id: String = "",
    val isIdDuplicated: Boolean? = null,
    val password: String = "",
    val passwordConfirm: String = ""
)
