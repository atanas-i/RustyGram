package dev.rustybite.rustygram.presentation.registration_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EmailScreen(modifier: Modifier = Modifier) {
    val uiState = RegistrationUiState()
    Scaffold { paddingValues ->
        EmailContent(
            uiState = uiState,
            onEmailChange = {},
            onSubmitEmail = { /*TODO*/ },
            onHaveAccountClicked = { /*TODO*/ },
            onSignUpWithPhone = { /*TODO*/ },
            modifier = modifier
                .padding(paddingValues)
        )
    }
}