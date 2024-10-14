package dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import dev.rustybite.rustygram.util.RustyEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BirthdayScreen(
    onNavigate: (RustyEvents.OnBoardingNavigate) -> Unit,
    onPopBackStack: (RustyEvents.PopBackStack) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateProfileViewModel
) {
    val uiState = viewModel.uiState.collectAsState().value
    val appEvent = viewModel.event

    LaunchedEffect(appEvent) {
        appEvent.collectLatest { event ->
            when(event) {
                is RustyEvents.OnBoardingNavigate -> onNavigate(event)
                is RustyEvents.PopBackStack -> onPopBackStack(event)
                is RustyEvents.ShowSnackBar -> Unit
                is RustyEvents.ShowToast -> Unit
                is RustyEvents.BottomScreenNavigate -> Unit
                is RustyEvents.Navigate -> Unit
            }
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .consumeWindowInsets(paddingValues = paddingValues)
        ) {
            BirthDayContent(
                uiState = uiState,
                onBirthDateChange = viewModel::onBirthDateChange,
                onNextClicked = viewModel::navigateToFullNameScreen,
                onHaveAccountClicked = viewModel::onHaveAccountClicked
            )
        }
    }
}