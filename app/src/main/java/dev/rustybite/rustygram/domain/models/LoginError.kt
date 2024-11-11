package dev.rustybite.rustygram.domain.models

import dev.rustybite.rustygram.data.dtos.util.LoginErrorDto

data class LoginError(
    val error: String,
    val errorDescription: String
)

fun LoginErrorDto.toLoginError(): LoginError = LoginError(
    error = error,
    errorDescription = errorDescription
)
