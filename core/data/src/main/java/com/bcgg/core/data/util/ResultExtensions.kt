package com.bcgg.core.data.util

import com.bcgg.core.data.response.ErrorResponse
import com.bcgg.core.util.Result
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException

fun <T> HttpException.toErrorMessage() : Result.Failure<T> {
    return Result.Failure(
        response()?.errorBody()?.string() ?: "알 수 없는 오류가 발생했습니다."
    )

}