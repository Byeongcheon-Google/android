package com.bcgg.core.util.ext

import com.bcgg.core.util.Result
import com.bcgg.core.util.onFailure
import com.bcgg.core.util.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

suspend inline fun <T> Flow<Result<T>>.collectLatestWithLoading(
    isLoading: MutableStateFlow<Boolean>,
    crossinline block: suspend Result<T>.() -> Unit
) {
    isLoading.value = true
    collectLatest {
        isLoading.value = false
        with(it) {
            block()
        }
    }
}

suspend inline fun <T> Flow<Result<T>>.collectLatest(
    crossinline block: suspend Result<T>.() -> Unit
) {
    collectLatest {
        with(it) {
            block()
        }
    }
}

suspend inline fun <T> Flow<Result<T>>.collectLatest(
    crossinline onSuccess: suspend (value: T) -> Unit,
    crossinline onFailure: suspend (errorMessage: String) -> Unit,
) {
    collectLatest {
        it.onSuccess(onSuccess)
        it.onFailure(onFailure)
    }
}

suspend inline fun <T> Flow<Result<T>>.collect(
    crossinline onSuccess: suspend (value: T) -> Unit,
    crossinline onFailure: suspend (errorMessage: String) -> Unit
) {
    collect {
        it.onSuccess(onSuccess)
        it.onFailure(onFailure)
    }
}