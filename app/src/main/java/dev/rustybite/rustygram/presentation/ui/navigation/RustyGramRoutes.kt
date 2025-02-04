package dev.rustybite.rustygram.presentation.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class RustyGramRoutes {
    @Serializable
    sealed class BottomNavScreen : RustyGramRoutes() {
        @Serializable
        data object HomeGraph : BottomNavScreen()
        @Serializable
        data object Home : BottomNavScreen()
        @Serializable
        data object Search : BottomNavScreen()
        @Serializable
        data object AddPost : BottomNavScreen()
        @Serializable
        data object Reels : BottomNavScreen()
        @Serializable
        data object Profile : BottomNavScreen()
    }
    @Serializable
    sealed class OnBoardingRoutes : RustyGramRoutes() {
        @Serializable
        data object OnBoardingGraph : OnBoardingRoutes()
        @Serializable
        data object CreateProfile : OnBoardingRoutes()
        @Serializable
        data object CreateBirthDate : OnBoardingRoutes()
        @Serializable
        data object CreateFullName : OnBoardingRoutes()
        @Serializable
        data object CreateUsername : OnBoardingRoutes()
        @Serializable
        data object CreateProfilePicture : OnBoardingRoutes()
        @Serializable
        data object ChangePassword : OnBoardingRoutes()
        @Serializable
        data object Registration : OnBoardingRoutes()
        @Serializable
        data object Login : OnBoardingRoutes()
    }
    @Serializable
    data object EditScreen : RustyGramRoutes()
    @Serializable
    data object FinalizePostScreen : RustyGramRoutes()
}