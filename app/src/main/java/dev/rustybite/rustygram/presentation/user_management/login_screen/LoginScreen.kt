package dev.rustybite.rustygram.presentation.user_management.login_screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import dev.rustybite.rustygram.presentation.ui.components.LanguageOptions
import dev.rustybite.rustygram.util.RustyEvents
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    snackBarHostState: SnackbarHostState,
    sheetState: SheetState,
    onNavigate: (RustyEvents.Navigate) -> Unit,
    onPopBackStack: (RustyEvents.PopBackStack) -> Unit,
    focusManager: FocusManager,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(viewModel.events) {
        viewModel.events.collectLatest { event ->
            when(event) {
                is RustyEvents.Navigate -> onNavigate(event)
                is RustyEvents.PopBackStack -> onPopBackStack(event)
                is RustyEvents.ShowSnackBar -> snackBarHostState.showSnackbar(event.message)
                is RustyEvents.ShowToast -> Unit
            }
        }
    }



    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },

    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            if (uiState.isLanguageSelectionToggled) {
                ModalBottomSheet(
                    onDismissRequest = { viewModel.onCloseLanguageSelection() },
                    sheetState = sheetState,
                ) {
                    Column(
                        modifier = modifier
                    ) {
                        LanguageOptions(
                           languageOptions = uiState.languageOptions,
                            selectedOption = uiState.selectedOption,
                            onOptionSelected = {
                                viewModel.onOptionSelected(it)
                                viewModel.onSelectOption(it == uiState.selectedOption)
                            }
                        )
                    }
                }
            }
            LoginContent(
                uiState = uiState,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,
                onLogin = { viewModel.login(uiState.email, uiState.password) },
                onForgotPassword = {
                    viewModel.forgotPassword()
                },
                onSignUpClicked = { viewModel.onSignUpClicked() },
                onOpenLanguageSelection = { viewModel.onOpenLanguageSelection() },
                focusManager = focusManager,
                scrollState = scrollState,
                onShowPasswordClicked = { viewModel.onShowPasswordClicked() }
            )
        }
    }
}