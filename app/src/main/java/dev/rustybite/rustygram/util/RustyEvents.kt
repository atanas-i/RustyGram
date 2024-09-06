package dev.rustybite.rustygram.util

import dev.rustybite.rustygram.presentation.ui.navigation.BottomNavScreen
import dev.rustybite.rustygram.presentation.ui.navigation.OnBoardingRoutes
import dev.rustybite.rustygram.presentation.ui.navigation.RustyAppRoutes

sealed class RustyEvents {
    data class OnBoardingNavigate(val route: OnBoardingRoutes) : RustyEvents()
    data class BottomScreenNavigate(val route: BottomNavScreen) : RustyEvents()
    data class Navigate(val route: RustyAppRoutes) : RustyEvents()
    data class ShowSnackBar(val message: String) : RustyEvents()
    data class ShowToast(val message: String) : RustyEvents()
    data object PopBackStack : RustyEvents()
}