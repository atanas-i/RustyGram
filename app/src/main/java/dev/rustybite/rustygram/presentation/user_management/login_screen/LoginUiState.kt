package dev.rustybite.rustygram.presentation.user_management.login_screen

data class LoginUiState(
    val loading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isLanguageSelectionToggled: Boolean = false,
    val languageOptions: List<String> = listOf("English (US)", "French", "Portuguese", "Spanish", "Kiswahili"),
    val selectedOption: String = "English (US)",
    val selected: Boolean = false,
    val errorMessage: String? = null,
    val isPasswordVisible: Boolean = false
)
