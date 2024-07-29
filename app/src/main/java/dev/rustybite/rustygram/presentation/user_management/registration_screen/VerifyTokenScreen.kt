package dev.rustybite.rustygram.presentation.user_management.registration_screen

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import dev.rustybite.rustygram.util.RustyEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun VerifyTokenScreen(
    snackBarHostState: SnackbarHostState,
    onNavigate: (RustyEvents.Navigate) -> Unit,
    onPopBack: (RustyEvents.PopBackStack) -> Unit,
    viewModel: UserManagementViewModel,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState().value
    
    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when(event) {
                is RustyEvents.Navigate -> onNavigate(event)
                is RustyEvents.PopBackStack -> onPopBack(event)
                is RustyEvents.ShowSnackBar -> snackBarHostState.showSnackbar(event.message)
                is RustyEvents.ShowToast -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ){ paddingValues ->
        VerifyTokenContent(
            uiState = uiState,
            onTokenChange = viewModel::onTokenChange,
            onSubmitToken = {
                viewModel.verifyOtp(
                    type = "email",
                    email = uiState.email,
                    token = uiState.token
                )
            },
            onHaveAccountClicked = {
                viewModel.onHaveAccountClicked()
            },
            onResendToken = {
                viewModel.requestOtp(email = uiState.email)
            },
            modifier = modifier
                .consumeWindowInsets(paddingValues)
        )
    }
}