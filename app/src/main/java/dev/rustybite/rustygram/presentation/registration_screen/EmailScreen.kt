package dev.rustybite.rustygram.presentation.registration_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import dev.rustybite.rustygram.util.RustyEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EmailScreen(
    snackBarHostState: SnackbarHostState,
    navigateToVerifyOtp: (RustyEvents.Navigate) -> Unit,
    popBackStack: (RustyEvents) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when(event) {
                is RustyEvents.Navigate -> navigateToVerifyOtp(event)
                is RustyEvents.PopBackStack -> popBackStack(event)
                is RustyEvents.ShowSnackBar -> snackBarHostState.showSnackbar(event.message)
                is  RustyEvents.ShowToast -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            EmailContent(
                uiState = uiState,
                onEmailChange = viewModel::onEmailChange,
                onSubmitEmail = { viewModel.requestOtp(uiState.email) },
                onHaveAccountClicked = viewModel::onHaveAccountClicked,
                onSignUpWithPhone = viewModel::onSignUpWithPhone,
            )
        }
    }
}