package dev.rustybite.rustygram.presentation.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.ui.focus.FocusManager
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.rustybite.rustygram.presentation.user_management.login_screen.LoginScreen
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.BirthdayScreen
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.CreateFullNameScreen
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.CreateProfilePictureScreen
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.CreateUsernameScreen
import dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen.CreateProfileViewModel
import dev.rustybite.rustygram.presentation.user_management.profile.view_profile_screen.ProfileScreen
import dev.rustybite.rustygram.presentation.user_management.registration_screen.SignUpScreen
import dev.rustybite.rustygram.presentation.user_management.registration_screen.UserRegistrationViewModel

fun NavGraphBuilder.homeNavGraph(navHostController: NavHostController) {
    navigation<BottomNavScreen.HomeGraph>(startDestination = BottomNavScreen.Home) {
        composable<BottomNavScreen.Home> {  }
        composable<BottomNavScreen.Search> {  }
        composable<BottomNavScreen.AddPost> {  }
        composable<BottomNavScreen.Reels> {  }
        composable<BottomNavScreen.Profile> {
            ProfileScreen()
        }
    }
}


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
                onPopBackStack = {navHostController.popBackStack() },
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