package dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen

import android.net.Uri
import java.time.LocalDateTime

data class ProfileUiState(
    val loading: Boolean = false,
    val fullName: String = "",
    val username: String = "",
    val birthDate: String = LocalDateTime.now().toString(),
    val userProfileUri: Uri? = null,
    val errorMessage: String = ""
)
