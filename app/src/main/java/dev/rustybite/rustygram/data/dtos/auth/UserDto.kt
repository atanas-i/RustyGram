package dev.rustybite.rustygram.data.dtos.auth


import dev.rustybite.rustygram.domain.models.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class UserDto(
    @SerialName("app_metadata")
    val appMetadata: AppMetadata?,
    @SerialName("aud")
    val aud: String,
    @SerialName("confirmation_sent_at")
    val confirmationSentAt: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("email")
    val email: String,
    @SerialName("id")
    val id: String,
    @SerialName("identities")
    val identities: List<Identity>,
    @SerialName("is_anonymous")
    val isAnonymous: Boolean,
    @SerialName("phone")
    val phone: String,
    @SerialName("role")
    val role: String,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("user_metadata")
    val userMetadata: UserMetadata?
)

fun UserDto.toUser(): User = User(
    userId = id,
    confirmationSentAt = confirmationSentAt ?: "",
    email = email,
    isAnonymous = isAnonymous,
    phone = phone,
    role = role,
    createdAt = createdAt ?: LocalDateTime.now().toString(),
    updatedAt = updatedAt ?: LocalDateTime.now().toString(),
    isEmailVerified = userMetadata?.emailVerified ?: false
)