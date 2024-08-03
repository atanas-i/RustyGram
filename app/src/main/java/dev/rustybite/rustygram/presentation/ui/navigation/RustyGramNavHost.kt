package dev.rustybite.rustygram.presentation.ui.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.rustybite.rustygram.presentation.RustyGramViewModel
import dev.rustybite.rustygram.presentation.user_management.registration_screen.UserManagementViewModel
import dev.rustybite.rustygram.presentation.ui.components.RustyBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RustyGramNavHost(
    navHostController: NavHostController,
    snackBarHostState: SnackbarHostState,
    sheetState: SheetState,
    focusManager: FocusManager,
    mainViewModel: RustyGramViewModel,
    modifier: Modifier = Modifier,
    viewModel: UserManagementViewModel = hiltViewModel()
) {
    val uiState = mainViewModel.uiState.collectAsState().value
    val startDestination by remember(uiState) {
        derivedStateOf {
            if (uiState.isUserSignedIn && uiState.isUserOnboarded) {
                BottomNavScreen.HomeGraph
            } else {
                OnBoardingRoutes.OnBoardingGraph
            }
        }
    }

    LaunchedEffect(mainViewModel) {
        mainViewModel.initialize()
    }
    Scaffold(
        bottomBar = {
            if (uiState.isUserSignedIn && uiState.isUserOnboarded) {
                RustyBottomBar(
                    navHostController = navHostController
                )
            }
        },
    ) { paddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = startDestination,
            modifier = modifier
                .consumeWindowInsets(paddingValues)
        ) {
            onBoardingGraph(
                navHostController = navHostController,
                snackBarHostState = snackBarHostState,
                sheetState = sheetState,
                focusManager = focusManager,
                viewModel = viewModel
            )
            homeNavGraph(navHostController = navHostController)
        }
    }
}