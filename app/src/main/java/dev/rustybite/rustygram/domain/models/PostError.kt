package dev.rustybite.rustygram.domain.models

import com.google.gson.annotations.SerializedName
import dev.rustybite.rustygram.data.dtos.util.PostErrorDto

data class PostError(
    val code: String,
    val details: String,
    val hint: Any?,
    val message: String
)

fun PostErrorDto.toPostError(): PostError = PostError(
    code = code,
    details = details,
    hint = hint,
    message = message
)
