package dev.rustybite.rustygram.presentation

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.presentation.ui.navigation.RustyGramNavHost
import dev.rustybite.rustygram.presentation.ui.theme.RustyGramTheme
import io.github.jan.supabase.SupabaseClient
import javax.inject.Inject

@AndroidEntryPoint
class RustyGramActivity : ComponentActivity() {
    @Inject
    lateinit var sessionManager: SessionManager
    private val mainViewModel: RustyGramViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
       enableEdgeToEdge()
        splashScreen.apply {
            setKeepOnScreenCondition {
                mainViewModel.uiState.value.loading
                //mainViewModel.isSplashScreenReleased.value
                //!mainViewModel.uiState.value.isUserSignedIn && !mainViewModel.uiState.value.isUserOnboarded
            }
        }
        setContent {
            val navHostController = rememberNavController()
            val snackBarHostState = remember { SnackbarHostState() }
            val sheetState = rememberModalBottomSheetState()
            val focusManager = LocalFocusManager.current

            RustyGramTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    RustyGramNavHost(
                        navHostController = navHostController,
                        snackBarHostState = snackBarHostState,
                        sheetState = sheetState,
                        focusManager = focusManager,
                        mainViewModel = mainViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
//        val content: View = findViewById(android.R.id.content)
//        content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
//            override fun onPreDraw(): Boolean {
//                if (!mainViewModel.uiState.value.isUserSignedIn && !mainViewModel.uiState.value.isUserOnboarded) {
//                    content.viewTreeObserver.removeOnPreDrawListener(this)
//                    return true
//                } else {
//                    return false
//                }
//            }
//        })
    }
}
