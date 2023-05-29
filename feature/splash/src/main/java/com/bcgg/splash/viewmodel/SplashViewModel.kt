package com.bcgg.splash.viewmodel

import androidx.lifecycle.viewModelScope
import com.bcgg.core.domain.repository.UserRepository
import com.bcgg.core.ui.viewmodel.BaseViewModel
import com.bcgg.core.util.ext.collectLatest
import com.bcgg.core.util.onFailure
import com.bcgg.core.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {
    private val _tokenIsValid = MutableStateFlow<Boolean?>(null)
    val tokenIsValid = _tokenIsValid.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.getUser()
                .collectLatest {
                    onSuccess  {
                        _tokenIsValid.emit(true)
                    }
                    onFailure {
                        _tokenIsValid.emit(false)
                    }
                }
        }
    }
}