package dev.rustybite.rustygram.presentation.ui.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.ui.navigation.BottomNav
import dev.rustybite.rustygram.presentation.ui.navigation.BottomNavScreen

@Composable
fun RustyBottomBar(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route ?: BottomNav.Home.route::class.qualifiedName.orEmpty()
    NavigationBar(
        modifier = modifier
            .heightIn(40.dp),
        containerColor = backgroundColor,
        contentColor = contentColor,
        tonalElevation = 0.dp

    ) {
        BottomNav.entries.forEach { item ->
            val isSelected by remember(currentRoute) {
                derivedStateOf { currentRoute == item.route::class.qualifiedName }
            }
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navHostController.navigate(item.route) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.title),
                        modifier = modifier
                            .size(dimensionResource(id = R.dimen.bottom_bar_icon_size))
                    )
                },
                modifier = modifier,
            )
        }
    }
}