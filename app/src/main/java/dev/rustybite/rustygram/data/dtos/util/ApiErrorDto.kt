package dev.rustybite.rustygram.data.dtos.util


import dev.rustybite.rustygram.domain.models.RustyApiError
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorDto(
    @SerialName("code")
    val code: Int?,
    @SerialName("error_code")
    val errorCode: String?,
    @SerialName("msg")
    val msg: String?
)

fun ApiErrorDto.toApiError(): RustyApiError = RustyApiError(
    code = code ?: -1,
    errorCode = errorCode ?: "Unknown",
    message = msg ?: "An unknown error occurred"
)