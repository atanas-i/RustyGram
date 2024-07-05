package dev.rustybite.rustygram.presentation.registration_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OtpScreen(
    modifier: Modifier = Modifier
) {
    val uiState = RegistrationUiState()

    Scaffold { paddingValues ->
        OtpContent(
            uiState = uiState,
            onOtpChange = { /*TODO*/ },
            onSubmitOtp = { /*TODO*/ },
            onResendOtp = { /*TODO*/ },
            modifier = modifier
                .padding(paddingValues)
        )
    }
}