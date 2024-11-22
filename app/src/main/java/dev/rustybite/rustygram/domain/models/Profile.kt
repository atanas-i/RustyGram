package dev.rustybite.rustygram.domain.models

import dev.rustybite.rustygram.data.dtos.profile.ProfileDto

data class Profile(
    val birthDate: String,
    val fullName: String,
    val profileId: String,
    val userName: String,
    val userId: String,
    val userProfilePicture: String,
    val bio: String
)

fun ProfileDto.toProfile(): Profile = Profile(
    birthDate = birthDate,
    fullName = fullName,
    profileId = profileId,
    userName = userName,
    userId = userId,
    userProfilePicture = userProfilePicture,
    bio = bio
)
