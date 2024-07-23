package dev.rustybite.rustygram.domain.models

import dev.rustybite.rustygram.data.dtos.auth.UserDto
import dev.rustybite.rustygram.data.dtos.auth.VerifiedUserDto
import dev.rustybite.rustygram.data.dtos.auth.toUser
import kotlinx.serialization.SerialName

data class VerifiedUser(
    @SerialName("access_token")
    val accessToken: String,
    val expiresAt: Int,
    val expiresIn: Int,
    val refreshToken: String,
    val tokenType: String,
    val user: User
)

fun VerifiedUserDto.toVerifiedUser(): VerifiedUser = VerifiedUser(
    accessToken = accessToken,
    expiresAt = expiresAt,
    expiresIn = expiresIn,
    refreshToken = refreshToken,
    tokenType = tokenType,
    user = user.toUser()
)
