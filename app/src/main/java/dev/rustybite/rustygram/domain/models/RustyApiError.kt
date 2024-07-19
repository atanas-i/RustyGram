package dev.rustybite.rustygram.domain.models

data class RustyApiError(
    val code: Int,
    val errorCode: String,
    val message: String,
)