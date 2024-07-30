package dev.rustybite.rustygram.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.rustybite.rustygram.presentation.ui.navigation.BottomNavScreen
import dev.rustybite.rustygram.presentation.ui.navigation.RustyGramNavHost
import dev.rustybite.rustygram.presentation.ui.theme.RustyGramTheme

@AndroidEntryPoint
class RustyGramActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       enableEdgeToEdge()
        setContent {
            val bottomNavItems = listOf(
                BottomNavScreen.Home,
                BottomNavScreen.Search,
                BottomNavScreen.AddPost,
                BottomNavScreen.Reels,
                BottomNavScreen.Profile
            )
            val navHostController = rememberNavController()
            val snackBarHostState = remember { SnackbarHostState() }
            val sheetState = rememberModalBottomSheetState()
            val focusManager = LocalFocusManager.current
            RustyGramTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RustyGramNavHost(
                        navHostController = navHostController,
                        snackBarHostState = snackBarHostState,
                        sheetState = sheetState,
                        bottomNavItems = bottomNavItems,
                        focusManager = focusManager,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
