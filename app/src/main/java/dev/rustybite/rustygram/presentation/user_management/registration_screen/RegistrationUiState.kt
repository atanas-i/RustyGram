package dev.rustybite.rustygram.presentation.user_management.registration_screen

data class RegistrationUiState(
    val loading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val token: String = "",
    val errorMessage: String? = null,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false
)
