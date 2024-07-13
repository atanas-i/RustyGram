package dev.rustybite.rustygram.presentation.registration_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dev.rustybite.rustygram.util.RustyEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun VerifyOtpScreen(
    snackBarHostState: SnackbarHostState,
    navigateToCreatePassword: (RustyEvents.Navigate) -> Unit,
    popBackStack: (RustyEvents) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is RustyEvents.Navigate -> navigateToCreatePassword(event)
                is RustyEvents.PopBackStack -> popBackStack(event)
                is RustyEvents.ShowSnackBar -> snackBarHostState.showSnackbar(event.message)
                is RustyEvents.ShowToast -> Unit
            }
        }
    }

    Scaffold { paddingValues ->
        VerifyOtpContent(
            uiState = uiState,
            onOtpChange = viewModel::onOtpChange,
            onSubmitOtp = {
                viewModel.verifyOtp(
                    email = uiState.email,
                    token = uiState.otp
                )
            },
            onResendOtp = {
                viewModel.registerUser(uiState.email, uiState.password)
            },
            onHaveAccountClicked = { viewModel.onHaveAccountClicked() },
            modifier = modifier
                .padding(paddingValues)
        )
    }
}