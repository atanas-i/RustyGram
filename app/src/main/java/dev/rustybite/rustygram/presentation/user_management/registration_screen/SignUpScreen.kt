package dev.rustybite.rustygram.presentation.user_management.registration_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import dev.rustybite.rustygram.util.RustyEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    snackBarHostState: SnackbarHostState,
    onNavigate: (RustyEvents.OnBoardingNavigate) -> Unit,
    popBackStack: (RustyEvents) -> Unit,
    viewModel: UserRegistrationViewModel,
    focusManager: FocusManager,
    modifier: Modifier = Modifier,
    // = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when(event) {
                is RustyEvents.OnBoardingNavigate -> onNavigate(event)
                is RustyEvents.PopBackStack -> popBackStack(event)
                is RustyEvents.ShowSnackBar -> snackBarHostState.showSnackbar(event.message)
                is  RustyEvents.ShowToast -> Unit
                is RustyEvents.BottomScreenNavigate -> Unit
                is RustyEvents.Navigate -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .consumeWindowInsets(paddingValues)
        ) {
            SignUpContent(
                uiState = uiState,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,
                signUp = { viewModel.registerUser(uiState.email, uiState.password) },
                onHaveAccountClicked = viewModel::onHaveAccountClicked,
                onSignUpWithPhone = viewModel::onSignUpWithPhone,
                onShowPasswordClicked = { viewModel.onShowPasswordClicked() },
                focusManager = focusManager
            )
        }
    }
}