package dev.rustybite.rustygram.data.dtos.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Identity(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("email")
    val email: String,
    @SerialName("id")
    val id: String,
    @SerialName("identity_data")
    val identityData: IdentityData,
    @SerialName("identity_id")
    val identityId: String,
    @SerialName("last_sign_in_at")
    val lastSignInAt: String,
    @SerialName("provider")
    val provider: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("user_id")
    val userId: String
)