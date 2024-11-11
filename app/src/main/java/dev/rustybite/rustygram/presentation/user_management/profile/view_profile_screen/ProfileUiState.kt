package dev.rustybite.rustygram.presentation.user_management.profile.view_profile_screen

import dev.rustybite.rustygram.domain.models.Profile

data class ProfileUiState(
    val loading: Boolean = false,
    val profile: Profile? = null,
    val errorMessage: String = ""
)
