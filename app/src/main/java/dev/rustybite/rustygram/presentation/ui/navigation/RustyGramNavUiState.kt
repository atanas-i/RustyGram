package dev.rustybite.rustygram.presentation.ui.navigation

import co.touchlab.kermit.Message
import dev.rustybite.rustygram.domain.models.User

data class RustyGramNavUiState(
    val loading: Boolean = false,
    val isUserSignedIn: Boolean =  false,
    val isUserOnboarded: Boolean =  false,
    val userId: String = "",
    val errorMessage: String = "",
    val userProfilePicture: String = "",
)