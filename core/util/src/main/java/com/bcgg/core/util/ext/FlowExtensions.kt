package com.bcgg.core.util.ext

import com.bcgg.core.util.Result
import com.bcgg.core.util.onFailure
import com.bcgg.core.util.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

suspend inline fun <T> Flow<Result<T>>.collectOnSuccess(
    crossinline action: suspend (value: T) -> Unit
): Flow<Result<T>> {
    collect { result ->
        result.onSuccess { action(it) }
    }
    return this
}

suspend inline fun <T> Flow<Result<T>>.collectOnFailure(
    crossinline action: suspend (errorMessage: String) -> Unit
): Flow<Result<T>> {
    collect { result ->
        result.onFailure { action(it) }
    }
    return this
}

suspend fun <T> Flow<Result<T>>.withLoading(loadingMutableStateFlow: MutableStateFlow<Boolean>): Flow<Result<T>> {
    loadingMutableStateFlow.value = true
    collectLatest {
        loadingMutableStateFlow.value = false
    }
    return this
}
