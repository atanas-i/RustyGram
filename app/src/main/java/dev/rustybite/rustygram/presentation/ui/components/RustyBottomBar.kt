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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.ui.navigation.BottomNavScreen

@Composable
fun RustyBottomBar(
    navItems: List<BottomNavScreen>,
    onItemClick: (BottomNavScreen) -> Unit,
    currentRoute: String?,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground
) {
    NavigationBar(
        modifier = modifier
            .heightIn(40.dp),
        containerColor = backgroundColor,
        contentColor = contentColor,
        tonalElevation = 0.dp

    ) {
        navItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = stringResource(id = item.resourceId),
                        modifier = modifier
                            .size(dimensionResource(id = R.dimen.bottom_bar_icon_size))
                    )
                },
                modifier = modifier,
            )
        }
    }
}