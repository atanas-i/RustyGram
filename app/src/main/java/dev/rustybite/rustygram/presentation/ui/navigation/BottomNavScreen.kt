package dev.rustybite.rustygram.presentation.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.rustybite.rustygram.R
import kotlinx.serialization.Serializable

sealed class BottomNavScreen {
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


enum class BottomNav(@StringRes val title: Int, @DrawableRes val icon: Int, val route: BottomNavScreen) {
    Home(R.string.home_screen, R.drawable.home_outlined_icon, BottomNavScreen.Home),
    Search(R.string.search_screen, R.drawable.search_icon, BottomNavScreen.Search),
    AddPost(R.string.add_screen, R.drawable.add_outline_icon, BottomNavScreen.AddPost),
    Reels(R.string.reels_screen, R.drawable.reel_outline_icon, BottomNavScreen.Reels),
    Profile(R.string.profile_screen, R.drawable.profile_outlined_icon, BottomNavScreen.Profile)
}
