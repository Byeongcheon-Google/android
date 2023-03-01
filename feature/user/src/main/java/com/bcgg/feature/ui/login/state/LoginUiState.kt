package com.bcgg.feature.ui.login.state

data class LoginUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isLoginAvailable: Boolean = false
)
