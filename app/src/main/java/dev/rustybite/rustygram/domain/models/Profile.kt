package dev.rustybite.rustygram.domain.models

import dev.rustybite.rustygram.data.dtos.profile.ProfileDto

data class Profile(
    val birthDate: String,
    val createdAt: String,
    val email: String,
    val fullName: String,
    val profileId: String,
    val updatedAt: String,
    val userId: String,
    val userName: String,
    val userProfilePicture: String,
    val bio: String
)

fun ProfileDto.toProfile(): Profile = Profile(
    birthDate = birthDate,
    createdAt = createdAt,
    email = email,
    fullName = fullName,
    profileId = profileId,
    updatedAt = updatedAt,
    userId = userId,
    userName = userName,
    userProfilePicture = userProfilePicture,
    bio = bio
)
