package dev.rustybite.rustygram.presentation.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.ui.focus.FocusManager
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.rustybite.rustygram.domain.models.Profile
import dev.rustybite.rustygram.presentation.posts.create_post.CreatePostViewModel
import dev.rustybite.rustygram.presentation.posts.create_post.edit_photo.EditPhotoScreen
import dev.rustybite.rustygram.presentation.posts.create_post.finalize_post.FinalizePostScreen
import dev.rustybite.rustygram.presentation.posts.create_post.image_picker.ImageScreen
import dev.rustybite.rustygram.presentation.posts.fetch_posts.FeedsScreen
import dev.rustybite.rustygram.presentation.user_management.login_screen.LoginScreen
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.BirthdayScreen
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.CreateFullNameScreen
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.CreateProfilePictureScreen
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.CreateProfileViewModel
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.CreateUsernameScreen
import dev.rustybite.rustygram.presentation.user_management.profile.view_profile_screen.ProfileScreen
import dev.rustybite.rustygram.presentation.user_management.registration_screen.SignUpScreen
import dev.rustybite.rustygram.presentation.user_management.registration_screen.UserRegistrationViewModel

fun NavGraphBuilder.homeNavGraph(
    navHostController: NavHostController,
    snackBarHostState: SnackbarHostState,
    onUserCreatingPost: (Boolean) -> Unit,
    scrollState: ScrollState,
    profile: Profile?,
    viewModel: CreatePostViewModel
) {
    navigation<RustyGramRoutes.BottomNavScreen.HomeGraph>(startDestination = RustyGramRoutes.BottomNavScreen.Home) {
        composable<RustyGramRoutes.BottomNavScreen.Home> {
            FeedsScreen(
                snackBarHostState = snackBarHostState
            )
        }
        composable<RustyGramRoutes.BottomNavScreen.Search> { }
        composable<RustyGramRoutes.BottomNavScreen.AddPost> {
            ImageScreen(
                onNavigate = { event -> navHostController.navigate(event.route) },
                onPopBack = { navHostController.popBackStack() },
                onUserCreatingPost = onUserCreatingPost,
                viewModel = viewModel
            )
        }
        composable<RustyGramRoutes.BottomNavScreen.Reels> { }
        composable<RustyGramRoutes.BottomNavScreen.Profile> {
            ProfileScreen()
        }
        composable<RustyGramRoutes.EditScreen> {
            EditPhotoScreen(
                snackBarHostState = snackBarHostState,
                onNavigate = { event -> navHostController.navigate(event.route) },
                onPopBack = { navHostController.popBackStack() },
                viewModel = viewModel
            )
        }
        composable<RustyGramRoutes.FinalizePostScreen> {
            FinalizePostScreen(
                snackbarHostState = snackBarHostState,
                onUserCreatingPost = onUserCreatingPost,
                onNavigate = { events ->
                    navHostController.navigate(events.route)
                },
                onPopBack = { navHostController.popBackStack() },
                profile = profile,
                viewModel = viewModel
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.onBoardingGraph(
    navHostController: NavHostController,
    snackBarHostState: SnackbarHostState,
    sheetState: SheetState,
    scrollState: ScrollState,
    focusManager: FocusManager,
    viewModel: UserRegistrationViewModel,
    profileViewModel: CreateProfileViewModel,
) {
    navigation<RustyGramRoutes.OnBoardingRoutes.OnBoardingGraph>(RustyGramRoutes.OnBoardingRoutes.Login) {
        composable<RustyGramRoutes.OnBoardingRoutes.Registration> {
            SignUpScreen(
                snackBarHostState = snackBarHostState,
                onNavigate = { event -> navHostController.navigate(event.route) },
                popBackStack = { navHostController.popBackStack() },
                focusManager = focusManager,
                scrollState = scrollState,
                viewModel = viewModel,
            )
        }
        composable<RustyGramRoutes.OnBoardingRoutes.CreateBirthDate> {
            BirthdayScreen(
                onNavigate = { event -> navHostController.navigate(event.route) },
                onPopBackStack = { navHostController.popBackStack() },
                viewModel = profileViewModel
            )
        }
        composable<RustyGramRoutes.OnBoardingRoutes.CreateFullName> {
            CreateFullNameScreen(
                onNavigate = { event -> navHostController.navigate(event.route) },
                onPopBackStack = { navHostController.popBackStack() },
                focusManager = focusManager,
                viewModel = profileViewModel
            )
        }
        composable<RustyGramRoutes.OnBoardingRoutes.CreateUsername> {
            CreateUsernameScreen(
                onNavigate = { event -> navHostController.navigate(event.route) },
                onPopBackStack = { navHostController.popBackStack() },
                focusManager = focusManager,
                viewModel = profileViewModel
            )
        }
        composable<RustyGramRoutes.OnBoardingRoutes.CreateProfilePicture> {
            CreateProfilePictureScreen(
                onNavigate = { event -> navHostController.navigate(event.route) },
                onPopBackStack = { navHostController.popBackStack() },
                sheetState = sheetState,
                snackbarHostState = snackBarHostState,
                viewModel = profileViewModel
            )
        }
        composable<RustyGramRoutes.OnBoardingRoutes.Login> {
            LoginScreen(
                snackBarHostState = snackBarHostState,
                sheetState = sheetState,
                onNavigate = { event -> navHostController.navigate(event.route) },
                onPopBackStack = { navHostController.popBackStack() },
                focusManager = focusManager,
                scrollState = scrollState
            )
        }
        composable<RustyGramRoutes.OnBoardingRoutes.ChangePassword> {
            Text(text = "Change Password")
        }
    }
}