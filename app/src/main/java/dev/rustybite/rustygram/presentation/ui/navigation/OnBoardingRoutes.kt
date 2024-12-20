package dev.rustybite.rustygram.presentation.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class OnBoardingRoutes {
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