package dev.rustybite.rustygram.presentation.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.ui.focus.FocusManager
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.get
import androidx.navigation.navigation
import dev.rustybite.rustygram.domain.models.Profile
import dev.rustybite.rustygram.presentation.posts.create_post.CreatePostViewModel
import dev.rustybite.rustygram.presentation.posts.create_post.edit_photo.EditPhotoScreen
import dev.rustybite.rustygram.presentation.posts.create_post.finalize_post.FinalizePostScreen
import dev.rustybite.rustygram.presentation.posts.create_post.image_picker.ImageScreen
import dev.rustybite.rustygram.presentation.user_management.login_screen.LoginScreen
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.BirthdayScreen
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.CreateFullNameScreen
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.CreateProfilePictureScreen
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.CreateUsernameScreen
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.CreateProfileViewModel
import dev.rustybite.rustygram.presentation.user_management.profile.view_profile_screen.ProfileScreen
import dev.rustybite.rustygram.presentation.user_management.registration_screen.SignUpScreen
import dev.rustybite.rustygram.presentation.user_management.registration_screen.UserRegistrationViewModel

fun NavGraphBuilder.homeNavGraph(
    navHostController: NavHostController,
    snackBarHostState: SnackbarHostState,
    isUserCreatingPost: MutableState<Boolean>,
    profile: Profile?,
    viewModel: CreatePostViewModel
) {
    navigation<BottomNavScreen.HomeGraph>(startDestination = BottomNavScreen.Home) {
        composable<BottomNavScreen.Home> { }
        composable<BottomNavScreen.Search> { }
        composable<BottomNavScreen.AddPost> {
            ImageScreen(
                onNavigate = { event -> navHostController.navigate(event.route) },
                onPopBack = { navHostController.popBackStack() },
                viewModel = viewModel
            )
        }
        composable<BottomNavScreen.Reels> { }
        composable<BottomNavScreen.Profile> {
            ProfileScreen()
        }
        composable<RustyAppRoutes.EditScreen> {
            EditPhotoScreen(
                snackBarHostState = snackBarHostState,
                onNavigate = { event -> navHostController.navigate(event.route) },
                onPopBack = { navHostController.popBackStack() },
                viewModel = viewModel
            )
        }
        composable<RustyAppRoutes.FinalizePostScreen> {
            FinalizePostScreen(
                snackbarHostState = snackBarHostState,
                isUserCreatingPost = isUserCreatingPost,
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
    focusManager: FocusManager,
    viewModel: UserRegistrationViewModel,
    profileViewModel: CreateProfileViewModel,
) {
    navigation<OnBoardingRoutes.OnBoardingGraph>(OnBoardingRoutes.Login) {
        composable<OnBoardingRoutes.Registration> {
            SignUpScreen(
                snackBarHostState = snackBarHostState,
                onNavigate = { event -> navHostController.navigate(event.route) },
                popBackStack = { navHostController.popBackStack() },
                focusManager = focusManager,
                viewModel = viewModel,
            )
        }
        composable<OnBoardingRoutes.CreateBirthDate> {
            BirthdayScreen(
                onNavigate = { event -> navHostController.navigate(event.route) },
                onPopBackStack = { navHostController.popBackStack() },
                viewModel = profileViewModel
            )
        }
        composable<OnBoardingRoutes.CreateFullName> {
            CreateFullNameScreen(
                onNavigate = { event -> navHostController.navigate(event.route) },
                onPopBackStack = { navHostController.popBackStack() },
                focusManager = focusManager,
                viewModel = profileViewModel
            )
        }
        composable<OnBoardingRoutes.CreateUsername> {
            CreateUsernameScreen(
                onNavigate = { event -> navHostController.navigate(event.route) },
                onPopBackStack = { navHostController.popBackStack() },
                focusManager = focusManager,
                viewModel = profileViewModel
            )
        }
        composable<OnBoardingRoutes.CreateProfilePicture> {
            CreateProfilePictureScreen(
                onNavigate = { event -> navHostController.navigate(event.route) },
                onPopBackStack = { navHostController.popBackStack() },
                sheetState = sheetState,
                snackbarHostState = snackBarHostState,
                viewModel = profileViewModel
            )
        }
        composable<OnBoardingRoutes.Login> {
            LoginScreen(
                snackBarHostState = snackBarHostState,
                sheetState = sheetState,
                onNavigate = { event -> navHostController.navigate(event.route) },
                onPopBackStack = { navHostController.popBackStack() },
                focusManager = focusManager
            )
        }
        composable<OnBoardingRoutes.ChangePassword> {
            Text(text = "Change Password")
        }
    }
}