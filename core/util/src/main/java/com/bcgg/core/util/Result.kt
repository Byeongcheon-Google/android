package com.bcgg.core.util

import retrofit2.HttpException

sealed class Result<T> {
    class Success<T>(val data: T) : Result<T>()
    class Failure<T>(val errorMessage: String) : Result<T>()

    val isSuccess: Boolean get() = this is Success<*>
    val isFailure: Boolean get() = this is Failure
}

suspend inline fun <T> Result<T>.onSuccess(crossinline onSuccess: suspend (T) -> Unit): Result<T> {
    if (this is Result.Success<T>) onSuccess(data)
    return this
}

suspend inline fun <T> Result<T>.onFailure(crossinline onSuccess: suspend (String) -> Unit): Result<T> {
    if (this is Result.Failure) onSuccess(errorMessage)
    return this
}

inline fun <T, R> Result<T>.map(crossinline map: (T) -> R): Result<R> {
    if (this is Result.Success<T>) return Result.Success(map(data))

    return Result.Failure((this as Result.Failure).errorMessage)
}

fun <T> Throwable.toFailure() = Result.Failure<T>(this.localizedMessage ?: "Unknown error")
