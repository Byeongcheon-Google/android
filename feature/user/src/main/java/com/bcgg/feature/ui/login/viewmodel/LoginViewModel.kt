package com.bcgg.feature.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcgg.core.domain.model.state.UserPasswordValidationState
import com.bcgg.core.domain.repository.UserRepository
import com.bcgg.core.domain.usecase.user.UserIdValidationUseCase
import com.bcgg.core.domain.usecase.user.UserPasswordValidationUseCase
import com.bcgg.core.ui.util.stateflow.stateFlowOf
import com.bcgg.core.ui.viewmodel.BaseViewModel
import com.bcgg.core.util.ext.collectOnFailure
import com.bcgg.core.util.ext.collectOnSuccess
import com.bcgg.core.util.ext.withLoading
import com.bcgg.feature.ui.login.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userIdValidationUseCase: UserIdValidationUseCase,
    private val userPasswordValidationUseCase: UserPasswordValidationUseCase,
    private val userRepository: UserRepository
) : BaseViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _loginCompletedEvent = MutableSharedFlow<Unit>()
    val loginCompletedEvent = _loginCompletedEvent.asSharedFlow()

    fun setId(id: String) {
        _loginUiState.value = _loginUiState.value.copy(
            id = id,
            isLoginAvailable = getLoginAvailableState(id, _loginUiState.value.password)
        )
    }

    fun setPassword(password: String) {
        _loginUiState.value = _loginUiState.value.copy(
            password = password,
            isLoginAvailable = getLoginAvailableState(_loginUiState.value.id, password)
        )
    }

    fun login() {
        viewModelScope.launch {
            _loginUiState.value = _loginUiState.value.copy(isLoading = true)
            userRepository.login(
                id = _loginUiState.value.id,
                passwordHashed = _loginUiState.value.password
            )
                .withLoading(_isLoading)
                .collectOnSuccess {
                    _loginCompletedEvent.emit(Unit)
                }.collectOnFailure {
                    _errorMessage.emit(it)
                }
            _loginUiState.value = _loginUiState.value.copy(isLoading = false)
        }
    }

    private fun getLoginAvailableState(id: String, password: String) =
        userIdValidationUseCase(id) && userPasswordValidationUseCase(password) == UserPasswordValidationState.OK
}
