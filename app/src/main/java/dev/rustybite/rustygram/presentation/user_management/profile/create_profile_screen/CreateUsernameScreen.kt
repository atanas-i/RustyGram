package dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import dev.rustybite.rustygram.util.RustyEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CreateUsernameScreen(
    onNavigate: (RustyEvents.Navigate) -> Unit,
    onPopBackStack: (RustyEvents.PopBackStack) -> Unit,
    focusManager: FocusManager,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val appEvent = viewModel.event

    LaunchedEffect(appEvent) {
        appEvent.collectLatest { event ->
            when(event) {
                is RustyEvents.Navigate -> onNavigate(event)
                is RustyEvents.PopBackStack -> onPopBackStack(event)
                is RustyEvents.ShowSnackBar -> Unit
                is RustyEvents.ShowToast -> Unit
            }
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .consumeWindowInsets(paddingValues)
        ) {
            CreateUsernameContent(
                uiState = uiState,
                onUsernameChange = viewModel::onUsernameChange,
                onNextClicked = { viewModel.navigateToProfilePictureScreen(uiState.username) },
                onHaveAccountClicked = viewModel::onHaveAccountClicked,
                focusManager = focusManager
            )
        }
    }
}