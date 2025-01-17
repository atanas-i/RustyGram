package dev.rustybite.rustygram.presentation.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.rustybite.rustygram.domain.models.Profile
import dev.rustybite.rustygram.presentation.RustyGramViewModel
import dev.rustybite.rustygram.presentation.posts.create_post.CreatePostViewModel
import dev.rustybite.rustygram.presentation.user_management.registration_screen.UserRegistrationViewModel
import dev.rustybite.rustygram.presentation.ui.components.RustyBottomBar
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.CreateProfileViewModel
import dev.rustybite.rustygram.util.RustyEvents
import io.ktor.util.reflect.instanceOf

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RustyGramNavHost(
    navHostController: NavHostController,
    snackBarHostState: SnackbarHostState,
    sheetState: SheetState,
    focusManager: FocusManager,
    profile: Profile?,
    mainViewModel: RustyGramViewModel,
    modifier: Modifier = Modifier,
    viewModel: UserRegistrationViewModel = hiltViewModel(),
    profileViewModel: CreateProfileViewModel = hiltViewModel(),
    createPostViewModel: CreatePostViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is RustyEvents.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
                is RustyEvents.BottomScreenNavigate -> {
                    navHostController.navigate(event.route)
                }
                is RustyEvents.OnBoardingNavigate -> {
                    navHostController.navigate(event.route)
                }
                is RustyEvents.Navigate -> {
                    navHostController.navigate(event.route)
                }
                else -> Unit
            }
        }
    }
    val uiState = mainViewModel.uiState.collectAsState().value
    val startDestination by remember(uiState.isUserSignedIn, uiState.isUserOnboarded) {
        derivedStateOf {
            when {
                uiState.isUserSignedIn && uiState.isUserOnboarded -> BottomNavScreen.HomeGraph
                else -> OnBoardingRoutes.OnBoardingGraph
            }
        }
    }
    val shouldShowBottomNav by remember(startDestination) {
        derivedStateOf {
            when {
                startDestination is BottomNavScreen.HomeGraph -> true
                else -> false
            }
        }
    }
    val isUserCreatingPost = remember { mutableStateOf(false) }
    LaunchedEffect(mainViewModel.event) {
        mainViewModel.event.collect { event ->
            when (event) {
                is RustyEvents.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomNav && !isUserCreatingPost.value) {
                RustyBottomBar(
                    navHostController = navHostController,
                    userProfilePicture = uiState.userProfilePicture,
                    isUserCreatingPost = isUserCreatingPost
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
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
                viewModel = viewModel,
                profileViewModel = profileViewModel
            )
            homeNavGraph(
                navHostController = navHostController,
                snackBarHostState = snackBarHostState,
                isUserCreatingPost = isUserCreatingPost,
                profile = profile,
                viewModel = createPostViewModel
            )
        }
    }
}