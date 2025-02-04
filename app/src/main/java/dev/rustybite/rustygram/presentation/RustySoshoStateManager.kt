package dev.rustybite.rustygram.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.rustybite.rustygram.presentation.ui.navigation.RustyGramRoutes
import dev.rustybite.rustygram.util.TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable

class RustySoshoStateManager {
    private val _shouldShowBottomNav = MutableStateFlow(false)
    val shouldShowBottomNav = _shouldShowBottomNav.asStateFlow()
    private val _isUserCreatingPost = MutableStateFlow(false)
    val isUserCreatingPost = _isUserCreatingPost.asStateFlow()
    private val _startDestination = MutableStateFlow<RustyGramRoutes>(RustyGramRoutes.OnBoardingRoutes.OnBoardingGraph)
    val startDestination = _startDestination.asStateFlow()

    fun onShowBottomNav(isShown: Boolean) {
        _shouldShowBottomNav.value = isShown
    }

    fun onUserCreatingPost(isCreating: Boolean) {
        _isUserCreatingPost.value = isCreating
    }

    fun onDestinationChange(route: RustyGramRoutes) {
        _startDestination.value = route
        Log.d(TAG, "onDestinationChange: Navigate to $route Route")
    }
}

@Composable
fun rememberRustySoshoStateManager(): RustySoshoStateManager {
    return remember {
        RustySoshoStateManager()
    }
}