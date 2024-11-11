package dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen

import android.net.Uri

data class CreateProfileUiState(
    val loading: Boolean = false,
    val fullName: String = "",
    val username: String = "",
    val birthDate: Long? = System.currentTimeMillis(),
    val userProfileUri: Uri? = null,
    val errorMessage: String = "",
    val userProfileUrl: String = ""
)
