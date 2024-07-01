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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dev.rustybite.rustygram.presentation.ui.navigation.BottomNavScreen
import dev.rustybite.rustygram.presentation.ui.navigation.RustyGramNavHost
import dev.rustybite.rustygram.presentation.ui.theme.RustyGramTheme

class RustyGramActivity : ComponentActivity() {
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
            RustyGramTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RustyGramNavHost(
                        navHostController = navHostController,
                        bottomNavItems = bottomNavItems,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
