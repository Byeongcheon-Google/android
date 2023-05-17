package com.bcgg.core.domain.usecase.user

import javax.inject.Inject

class UserIdValidationUseCase @Inject constructor() {
    private var lastId: String? = null
    private var lastResult: Boolean = false
    operator fun invoke(id: String): Boolean {
        if(lastId == id) return lastResult

        lastId = id
        lastResult = id.isNotBlank()

        return lastResult
    }
}
