package dev.rustybite.rustygram.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsIgnoringVisibility
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.presentation.ui.navigation.RustyGramNavHost
import dev.rustybite.rustygram.presentation.ui.navigation.RustyGramRoutes
import dev.rustybite.rustygram.presentation.ui.theme.RustyGramTheme
import dev.rustybite.rustygram.util.TAG
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RustyGramActivity : ComponentActivity() {
    @Inject
    lateinit var sessionManager: SessionManager
    lateinit var navHostController: NavHostController
    lateinit var rustySoshoStateManager: RustySoshoStateManager
    private val mainViewModel: RustyGramViewModel by viewModels()
    //private val isUserSignedIn: Boolean? = null

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
       enableEdgeToEdge()
        splashScreen.apply {
            setKeepOnScreenCondition {
                mainViewModel.uiState.value.isSplashScreenReleased
            }
        }
        setContent {
            navHostController = rememberNavController()
            val snackBarHostState = remember { SnackbarHostState() }
            val sheetState = rememberModalBottomSheetState()
            val scrollState = rememberScrollState()
            val focusManager = LocalFocusManager.current
            val uiState = mainViewModel.uiState.collectAsState().value
            val events = mainViewModel.event
            val profile = uiState.profile
            rustySoshoStateManager = rememberRustySoshoStateManager()
            val startDestination = rustySoshoStateManager.startDestination.collectAsStateWithLifecycle().value

            RustyGramTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    RustyGramNavHost(
                        navHostController = navHostController,
                        snackBarHostState = snackBarHostState,
                        sheetState = sheetState,
                        focusManager = focusManager,
                        profile = profile,
                        uiState = uiState,
                        events = events,
                        scrollState = scrollState,
                        startDestination = startDestination,
                        rustySoshoStateManager = rustySoshoStateManager,
                        modifier = Modifier.padding(innerPadding)
                    )

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            val isUserSignedIn = sessionManager.isUserSignedIn.first()
            val accessToken = sessionManager.accessToken.first()
            val refreshToken = sessionManager.refreshToken.first()
            val expiresAt = sessionManager.expiresAt.first()

            if (isUserSignedIn == true) {
                if (sessionManager.isAccessTokenExpired(accessToken, expiresAt)) {
                    Log.d(TAG, "onStart: Token expired.. refreshing it")
                    mainViewModel.refreshAccessToken(refreshToken)
                }
                mainViewModel.getUser(accessToken)
            }
            if (isUserSignedIn == true) {
                Log.d(TAG, "onStart: User logged in.. navigating to Home")
                rustySoshoStateManager.onShowBottomNav(isShown = true)
                rustySoshoStateManager.onDestinationChange(RustyGramRoutes.BottomNavScreen.HomeGraph)
            } else {
                Log.d(TAG, "onStart: User logged out.. navigating to Login")
                rustySoshoStateManager.onShowBottomNav(isShown = false)
                rustySoshoStateManager.onDestinationChange(RustyGramRoutes.OnBoardingRoutes.OnBoardingGraph)
            }
        }
    }
}
