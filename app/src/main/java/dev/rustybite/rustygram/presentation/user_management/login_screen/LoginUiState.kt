package dev.rustybite.rustygram.presentation.user_management.login_screen

data class LoginUiState(
    val loading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val errorMessage: String = "",
)
