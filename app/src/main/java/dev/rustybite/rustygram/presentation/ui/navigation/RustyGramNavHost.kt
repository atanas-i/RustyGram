package dev.rustybite.rustygram.presentation.ui.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.rustybite.rustygram.presentation.registration_screen.VerifyTokenScreen
import dev.rustybite.rustygram.presentation.registration_screen.RegistrationViewModel
import dev.rustybite.rustygram.presentation.registration_screen.SignUpScreen

@Composable
fun RustyGramNavHost(
    navHostController: NavHostController,
    snackBarHostState: SnackbarHostState,
    bottomNavItems: List<BottomNavScreen>,
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val currentRoute = navHostController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
//        bottomBar = {
//            RustyBottomBar(
//                navItems = bottomNavItems,
//                currentRoute = currentRoute,
//                onItemClick = { item ->
//                    navHostController.navigate(item.route) {
//                        popUpTo(navHostController.graph.startDestinationId) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                }
//            )
//        },
    ) { paddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = RustyRoutes.Registration,
            modifier = modifier
                .consumeWindowInsets(paddingValues)
        ) {
            composable<RustyRoutes.Registration> {
                SignUpScreen(
                    snackBarHostState = snackBarHostState,
                    onNavigate = { event -> navHostController.navigate(event.route) },
                    popBackStack = { navHostController.popBackStack() },
                    viewModel = viewModel,
                )
            }
            composable<RustyRoutes.VerifyOtp> {
//                VerifyOtpScreen(
//                    snackBarHostState = snackBarHostState,
//                    navigateToCreatePassword = { event -> navHostController.navigate(event.route) },
//                    popBackStack = { navHostController.popBackStack() },
//                    viewModel = viewModel
//                )
                VerifyTokenScreen(
                    snackBarHostState = snackBarHostState,
                    viewModel = viewModel,
                    onNavigate = { navHostController.navigate(it.route) },
                    onPopBack = { navHostController.popBackStack() },

                )
            }
            composable<RustyRoutes.CreateProfile> { 
                Text(text = "Create Profile")
            }
            composable<RustyRoutes.Login> {
                Text(text = "Please Login")
            }
            composable(BottomNavScreen.Home.route) {  }
            composable(BottomNavScreen.Search.route) {  }
            composable(BottomNavScreen.AddPost.route) {  }
            composable(BottomNavScreen.Reels.route) {  }
            composable(BottomNavScreen.Profile.route) {  }
        }
    }
}