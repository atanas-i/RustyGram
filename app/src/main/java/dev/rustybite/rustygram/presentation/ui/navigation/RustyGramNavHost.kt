package dev.rustybite.rustygram.presentation.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.user_management.registration_screen.VerifyTokenScreen
import dev.rustybite.rustygram.presentation.user_management.registration_screen.UserManagementViewModel
import dev.rustybite.rustygram.presentation.user_management.registration_screen.SignUpScreen
import dev.rustybite.rustygram.presentation.ui.components.RustyBottomBar
import dev.rustybite.rustygram.presentation.ui.components.RustyPrimaryButton
import dev.rustybite.rustygram.presentation.user_management.login_screen.LoginScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RustyGramNavHost(
    navHostController: NavHostController,
    snackBarHostState: SnackbarHostState,
    sheetState: SheetState,
    bottomNavItems: List<BottomNavScreen>,
    modifier: Modifier = Modifier,
    viewModel: UserManagementViewModel = hiltViewModel()
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
                LoginScreen(
                    snackBarHostState = snackBarHostState,
                    sheetState = sheetState,
                    onNavigate = { event -> navHostController.navigate(event.route) },
                    onPopBackStack = { navHostController.popBackStack() }
                )
            }
            composable<RustyRoutes.ChangePassword> {
                Text(text = "Change Password")
            }
            composable(BottomNavScreen.Home.route) {  }
            composable(BottomNavScreen.Search.route) {  }
            composable(BottomNavScreen.AddPost.route) {  }
            composable(BottomNavScreen.Reels.route) {  }
            composable(BottomNavScreen.Profile.route) { 
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .consumeWindowInsets(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Profile Screen")
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
                    RustyPrimaryButton(text = "Logout", onClick = {  }, loading = viewModel.uiState.collectAsState().value.loading)
                }
            }
        }
    }
}