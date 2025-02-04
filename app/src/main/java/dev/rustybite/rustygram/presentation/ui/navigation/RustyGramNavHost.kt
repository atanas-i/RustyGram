package dev.rustybite.rustygram.presentation.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.rustybite.rustygram.domain.models.Profile
import dev.rustybite.rustygram.presentation.RustySoshoStateManager
import dev.rustybite.rustygram.presentation.posts.create_post.CreatePostViewModel
import dev.rustybite.rustygram.presentation.rememberRustySoshoStateManager
import dev.rustybite.rustygram.presentation.ui.components.RustyBottomBar
import dev.rustybite.rustygram.presentation.user_management.login_screen.LoginScreen
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.CreateProfileViewModel
import dev.rustybite.rustygram.presentation.user_management.registration_screen.UserRegistrationViewModel
import dev.rustybite.rustygram.util.RustyEvents
import kotlinx.coroutines.flow.Flow

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RustyGramNavHost(
    navHostController: NavHostController,
    snackBarHostState: SnackbarHostState,
    sheetState: SheetState,
    focusManager: FocusManager,
    scrollState: ScrollState,
    profile: Profile?,
    uiState: RustyGramNavUiState,
    rustySoshoStateManager: RustySoshoStateManager,
    startDestination: RustyGramRoutes,
    events: Flow<RustyEvents>,
    modifier: Modifier = Modifier,
    viewModel: UserRegistrationViewModel = hiltViewModel(),
    profileViewModel: CreateProfileViewModel = hiltViewModel(),
    createPostViewModel: CreatePostViewModel = hiltViewModel()
) {
    val shouldShowBottomNav =
        rustySoshoStateManager.shouldShowBottomNav.collectAsStateWithLifecycle().value
    val isUserCreatingPost =
        rustySoshoStateManager.isUserCreatingPost.collectAsStateWithLifecycle().value

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is RustyEvents.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }

                is RustyEvents.Navigate -> {
                    navHostController.navigate(event.route)
                }

                else -> Unit
            }
        }
    }
    //val uiState = mainViewModel.uiState.collectAsState().value
//    val startDestination by remember(uiState.isUserSignedIn, uiState.isUserOnboarded) {
//        derivedStateOf {
//            when {
//                uiState.isUserSignedIn && uiState.isUserOnboarded -> BottomNavScreen.HomeGraph
//                else -> OnBoardingRoutes.OnBoardingGraph
//            }
//        }
//    }
//    val shouldShowBottomNav by remember(startDestination) {
//        derivedStateOf {
//            when {
//                startDestination is BottomNavScreen.HomeGraph -> true
//                else -> false
//            }
//        }
//    }

    LaunchedEffect(events) {
        events.collect { event ->
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
            if (shouldShowBottomNav && !isUserCreatingPost) {
                //if (!isUserCreatingPost.value) {
                RustyBottomBar(
                    navHostController = navHostController,
                    userProfilePicture = uiState.userProfilePicture,
                    onUserCreatingPost = { isUserCreatingPost ->
                        rustySoshoStateManager.onUserCreatingPost(isUserCreatingPost)
                    },
                    //modifier = modifier
                    //isUserCreatingPost = isUserCreatingPost
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
                scrollState = scrollState,
                viewModel = viewModel,
                profileViewModel = profileViewModel
            )
            homeNavGraph(
                navHostController = navHostController,
                snackBarHostState = snackBarHostState,
                onUserCreatingPost = { isUserCreatingPost ->
                    rustySoshoStateManager.onUserCreatingPost(isUserCreatingPost)
                },
                profile = profile,
                scrollState = scrollState,
                viewModel = createPostViewModel
            )
        }
    }
}