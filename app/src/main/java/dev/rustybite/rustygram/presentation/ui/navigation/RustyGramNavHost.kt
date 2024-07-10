package dev.rustybite.rustygram.presentation.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.rustybite.rustygram.presentation.registration_screen.EmailScreen
import dev.rustybite.rustygram.presentation.registration_screen.OtpScreen
import dev.rustybite.rustygram.presentation.ui.components.RustyBottomBar

@Composable
fun RustyGramNavHost(
    navHostController: NavHostController,
    snackBarHostState: SnackbarHostState,
    bottomNavItems: List<BottomNavScreen>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentRoute = navHostController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        bottomBar = {
            RustyBottomBar(
                navItems = bottomNavItems,
                currentRoute = currentRoute,
                onItemClick = { item ->
                    navHostController.navigate(item.route) {
                        popUpTo(navHostController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = RustyRoutes.RequestOtp,
            modifier = modifier.padding(paddingValues)
        ) {
            composable<RustyRoutes.RequestOtp> {
                EmailScreen(
                    snackBarHostState = snackBarHostState,
                    onNavigate = { navHostController.navigate(it.route) },
                    //navigateToLogin = { navHostController.navigate(RustyRoutes.Login)},
                    popBackStack = { navHostController.popBackStack() },
                )
            }
            composable<RustyRoutes.VerifyOtp> {
                OtpScreen(
                    snackBarHostState = snackBarHostState,
                    navigateToCreatePassword = { navHostController.navigate(it.route) },
                    popBackStack = { navHostController.popBackStack() }
                )
            }
            composable<RustyRoutes.Login> {  }
            composable(BottomNavScreen.Home.route) {  }
            composable(BottomNavScreen.Search.route) {  }
            composable(BottomNavScreen.AddPost.route) {  }
            composable(BottomNavScreen.Reels.route) {  }
            composable(BottomNavScreen.Profile.route) {  }
        }
    }
}