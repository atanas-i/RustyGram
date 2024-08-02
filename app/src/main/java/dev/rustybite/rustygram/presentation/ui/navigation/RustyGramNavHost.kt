package dev.rustybite.rustygram.presentation.ui.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.presentation.user_management.registration_screen.VerifyTokenScreen
import dev.rustybite.rustygram.presentation.user_management.registration_screen.UserManagementViewModel
import dev.rustybite.rustygram.presentation.user_management.registration_screen.SignUpScreen
import dev.rustybite.rustygram.presentation.ui.components.RustyBottomBar
import dev.rustybite.rustygram.presentation.user_management.login_screen.LoginScreen
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RustyGramNavHost(
    navHostController: NavHostController,
    snackBarHostState: SnackbarHostState,
    sheetState: SheetState,
    sessionManager: SessionManager,
    focusManager: FocusManager,
    modifier: Modifier = Modifier,
    viewModel: UserManagementViewModel = hiltViewModel()
) {
    var isUserSignedIn by remember { mutableStateOf(false) }
    var isUserOnboaded by remember { mutableStateOf(true) }

    LaunchedEffect(sessionManager) {
        isUserSignedIn = sessionManager.isUserSignedIn.first() ?: false
        isUserOnboaded = sessionManager.isUserOnboarded.first() ?: true
    }

    Scaffold(
        bottomBar = {
            if (isUserSignedIn && isUserOnboaded) {
                RustyBottomBar(
                    navHostController = navHostController
                )
            }
        },
    ) { paddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = if (isUserSignedIn && isUserOnboaded) BottomNavScreen.HomeGraph else OnBoardingRoutes.OnBoardingGraph,
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