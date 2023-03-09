package com.bcgg.core.domain.usecase.user

import javax.inject.Inject

class UserIdValidationUseCase @Inject constructor() {
    operator fun invoke(id: String): Boolean {
        return id.isNotBlank()
    }
}
