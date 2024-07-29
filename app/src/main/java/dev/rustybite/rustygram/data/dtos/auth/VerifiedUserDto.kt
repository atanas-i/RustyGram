package dev.rustybite.rustygram.data.dtos.auth


import com.google.gson.annotations.SerializedName
import dev.rustybite.rustygram.domain.models.VerifiedUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//@Serializable
data class VerifiedUserDto(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_at")
    val expiresAt: Long,
    @SerializedName("expires_in")
    val expiresIn: Long,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("user")
    val user: UserDto
)

fun VerifiedUserDto.toVerifiedUser(): VerifiedUser = VerifiedUser(
    accessToken = accessToken,
    expiresAt = expiresAt,
    expiresIn = expiresIn,
    refreshToken = refreshToken,
    tokenType = tokenType,
    user = user.toUser()
)