package dev.rustybite.rustygram.domain.models

data class VerifiedUser(
    val accessToken: String,
    val expiresAt: Long,
    val expiresIn: Long,
    val refreshToken: String,
    val tokenType: String,
    val user: User
)
