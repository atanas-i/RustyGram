package dev.rustybite.rustygram.data.dtos.auth


import com.google.gson.annotations.SerializedName
import dev.rustybite.rustygram.domain.models.User

data class UserDto(
    @SerializedName("app_metadata")
    val appMetadata: AppMetadata,
    @SerializedName("aud")
    val aud: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_confirmed_at")
    val emailConfirmedAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("identities")
    val identities: List<Identity>,
    @SerializedName("is_anonymous")
    val isAnonymous: Boolean,
    @SerializedName("last_sign_in_at")
    val lastSignInAt: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_metadata")
    val userMetadata: UserMetadata
)

fun UserDto.toUser(): User = User(
    userId = id,
    confirmationSentAt = emailConfirmedAt,
    email = email,
    isAnonymous = isAnonymous,
    phone = phone,
    role = role,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isEmailVerified = userMetadata.emailVerified
)