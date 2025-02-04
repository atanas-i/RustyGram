package dev.rustybite.rustygram.presentation.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.rustybite.rustygram.R

enum class BottomNav(@StringRes val title: Int, @DrawableRes val icon: Int, val route: RustyGramRoutes.BottomNavScreen) {
    Home(R.string.home_screen, R.drawable.home_outlined_icon, RustyGramRoutes.BottomNavScreen.Home),
    Search(R.string.search_screen, R.drawable.search_icon, RustyGramRoutes.BottomNavScreen.Search),
    AddPost(R.string.add_screen, R.drawable.add_outline_icon, RustyGramRoutes.BottomNavScreen.AddPost),
    Reels(R.string.reels_screen, R.drawable.reel_outline_icon, RustyGramRoutes.BottomNavScreen.Reels),
    Profile(R.string.profile_screen, R.drawable.profile_outlined_icon, RustyGramRoutes.BottomNavScreen.Profile)
}
