package dev.rustybite.rustygram.data.dtos.profile


import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("birth_date")
    val birthDate: Any?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("profile_id")
    val profileId: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("user_profile_picture")
    val userProfilePicture: String
)