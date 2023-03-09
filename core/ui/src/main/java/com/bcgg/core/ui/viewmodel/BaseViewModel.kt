package com.bcgg.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel : ViewModel() {
    @Suppress("VariableNaming")
    protected val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
}
