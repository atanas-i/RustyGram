package dev.rustybite.rustygram.presentation.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class RustyRoutes {
    @Serializable
    data object CreateProfile: RustyRoutes()
    @Serializable
    data object RequestOtp : RustyRoutes()
    @Serializable
    data object VerifyOtp : RustyRoutes()
    @Serializable
    data object CreatePassword : RustyRoutes()
    @Serializable
    data object Registration : RustyRoutes()
    @Serializable
    data object Login : RustyRoutes()

}