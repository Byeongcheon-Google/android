package com.bcgg.feature.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcgg.core.domain.repository.UserRepository
import com.bcgg.core.domain.usecase.user.UserEmailValidationUseCase
import com.bcgg.core.ui.util.stateflow.stateFlowOf
import com.bcgg.feature.ui.login.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userEmailValidationUseCase: UserEmailValidationUseCase,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState by stateFlowOf(_loginUiState)

    fun setEmail(email: String) {
        _loginUiState.value = _loginUiState.value.copy(
            email = email,
            isLoginAvailable = userEmailValidationUseCase(email)
        )
    }

    fun setPassword(password: String) {
        _loginUiState.value = _loginUiState.value.copy(password = password)
    }

    fun login() {
        viewModelScope.launch {
            _loginUiState.value = _loginUiState.value.copy(isLoading = true)
            userRepository.login(
                email = _loginUiState.value.email,
                password = _loginUiState.value.password
            )
            _loginUiState.value = _loginUiState.value.copy(isLoading = false)
        }
    }
}
