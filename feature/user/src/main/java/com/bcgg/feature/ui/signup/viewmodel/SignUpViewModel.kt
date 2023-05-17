package com.bcgg.feature.ui.signup.viewmodel

import androidx.lifecycle.viewModelScope
import com.bcgg.core.domain.repository.UserRepository
import com.bcgg.core.domain.usecase.user.SignUpUseCase
import com.bcgg.core.ui.viewmodel.BaseViewModel
import com.bcgg.core.util.ext.collectOnFailure
import com.bcgg.core.util.ext.collectOnSuccess
import com.bcgg.core.util.ext.withLoading
import com.bcgg.feature.ui.signup.state.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel() {
    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    val signUpUiState = _signUpUiState.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _signUpCompletedEvent = MutableSharedFlow<String>()
    val signUpCompletedEvent = _signUpCompletedEvent.asSharedFlow()

    var signUpProceed = false

    fun updateIdText(id: String) {
        _signUpUiState.value = signUpUiState.value.copy(id = id, isIdDuplicated = null)
    }

    fun updatePasswordText(password: String) {
        _signUpUiState.value = signUpUiState.value.copy(password = password)
    }

    fun updatePasswordConfirmText(passwordConfirm: String) {
        _signUpUiState.value = signUpUiState.value.copy(passwordConfirm = passwordConfirm)
    }

    fun checkIdIsDuplicated() = viewModelScope.launch {
        userRepository.isIdDuplicated(signUpUiState.value.id)
            .withLoading(_isLoading)
            .collectOnSuccess {
                _signUpUiState.value = signUpUiState.value.copy(
                    isIdDuplicated = it
                )
            }
            .collectOnFailure {
                _signUpUiState.value = signUpUiState.value.copy(
                    isIdDuplicated = null
                )
                _errorMessage.emit(it)
            }
    }

    fun signUp() = viewModelScope.launch {
        if (signUpProceed) return@launch
        signUpProceed = true
        signUpUseCase(
            id = signUpUiState.value.id,
            password = signUpUiState.value.password,
            passwordConfirm = signUpUiState.value.passwordConfirm
        )
            .withLoading(_isLoading)
            .collectOnSuccess {
                _signUpCompletedEvent.emit(signUpUiState.value.id)
            }
            .collectOnFailure {
                _errorMessage.emit(it)
                signUpProceed = false
            }
    }
}
