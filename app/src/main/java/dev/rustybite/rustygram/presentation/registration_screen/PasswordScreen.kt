package dev.rustybite.rustygram.presentation.registration_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PasswordScreen(
    modifier: Modifier = Modifier
) {
    val uiState = RegistrationUiState()

    Scaffold { paddingValues ->
        PasswordContent(
            uiState = uiState,
            onPasswordChange = { /*TODO*/ },
            onSubmitPassword = { /*TODO*/ },
            onHaveAccountClicked = { /*TODO*/ },
            modifier = modifier
                .padding(paddingValues)
        )
    }
}