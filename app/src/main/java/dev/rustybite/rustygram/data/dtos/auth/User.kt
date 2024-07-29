package dev.rustybite.rustygram.data.dtos.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("app_metadata")
    val appMetadata: AppMetadata,
    @SerialName("aud")
    val aud: String,
    @SerialName("confirmation_sent_at")
    val confirmationSentAt: String,
    @SerialName("confirmed_at")
    val confirmedAt: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("email")
    val email: String,
    @SerialName("email_confirmed_at")
    val emailConfirmedAt: String,
    @SerialName("id")
    val id: String,
    @SerialName("identities")
    val identities: List<Identity>,
    @SerialName("is_anonymous")
    val isAnonymous: Boolean,
    @SerialName("last_sign_in_at")
    val lastSignInAt: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("role")
    val role: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("user_metadata")
    val userMetadata: UserMetadata
)