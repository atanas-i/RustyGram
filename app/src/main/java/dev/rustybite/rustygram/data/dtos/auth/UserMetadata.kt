package dev.rustybite.rustygram.data.dtos.auth


import com.google.gson.annotations.SerializedName

data class UserMetadata(
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified")
    val emailVerified: Boolean,
    @SerializedName("phone_verified")
    val phoneVerified: Boolean,
    @SerializedName("sub")
    val sub: String
)