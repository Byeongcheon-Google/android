package com.bcgg.feature.ui.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.bcgg.core.domain.model.state.UserPasswordValidationState
import com.bcgg.core.domain.repository.UserRepository
import com.bcgg.core.domain.usecase.user.LoginUseCase
import com.bcgg.core.domain.usecase.user.UserIdValidationUseCase
import com.bcgg.core.domain.usecase.user.UserPasswordValidationUseCase
import com.bcgg.core.ui.viewmodel.BaseViewModel
import com.bcgg.core.util.ext.collectLatest
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
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

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
        _loginUiState.value = _loginUiState.value.copy(isLoginAvailable = false, isLoading = true)
        viewModelScope.launch {
            loginUseCase(
                id = _loginUiState.value.id,
                password = _loginUiState.value.password
            )
                .collectLatest(
                    onSuccess = {
                        _loginCompletedEvent.emit(Unit)
                    }, onFailure = {
                        _toastMessage.emit(it)
                        _loginUiState.value = _loginUiState.value.copy(isLoading = false, password = "")
                    }
                )
        }
    }

    fun showSignUpCompletedToastMessage() = viewModelScope.launch {
        _toastMessage.emit("회원가입에 성공하였습니다. 가입한 아이디와 비밀번호로 로그인해주세요.")
    }

    private fun getLoginAvailableState(id: String, password: String) =
        userIdValidationUseCase(id) && userPasswordValidationUseCase(password) == UserPasswordValidationState.OK
}
