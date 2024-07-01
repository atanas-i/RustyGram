package dev.rustybite.rustygram.presentation.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.rustybite.rustygram.R
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

sealed class BottomNavScreen(val route: String, @StringRes val resourceId: Int, @DrawableRes val iconId: Int) {
    data object Home : BottomNavScreen(route = "home", resourceId = R.string.home_screen, iconId = R.drawable.home_outlined_icon)
    data object Search : BottomNavScreen(route = "search", resourceId = R.string.search_screen, iconId = R.drawable.search_icon)
    data object AddPost : BottomNavScreen(route = "add_post", resourceId = R.string.add_screen, iconId = R.drawable.add_outline_icon)
    data object Reels : BottomNavScreen(route = "reels", resourceId = R.string.reels_screen, iconId = R.drawable.reel_outline_icon)
    data object Profile : BottomNavScreen(route = "profile", resourceId = R.string.profile_screen, iconId = R.drawable.profile_outlined_icon)
}
