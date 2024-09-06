package dev.rustybite.rustygram.domain.models

import com.google.gson.annotations.SerializedName
import dev.rustybite.rustygram.data.dtos.util.DatabaseResponseErrorDto

data class DatabaseResponseError(
    val code: String,
    val details: Any?,
    val hint: Any?,
    val message: String
)

fun DatabaseResponseErrorDto.toDatabaseResponseError(): DatabaseResponseError = DatabaseResponseError(
    code = code,
    details = details,
    hint = hint,
    message = message
)
