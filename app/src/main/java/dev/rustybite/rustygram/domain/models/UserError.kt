package dev.rustybite.rustygram.domain.models

import dev.rustybite.rustygram.data.dtos.util.UserErrorDto

data class UserError(
    val code: Int,
    val errorCode: String,
    val msg: String
)

fun UserErrorDto.toUserError(): UserError = UserError(
        code = this.code,
        errorCode = this.errorCode,
        msg = this.msg
)
