package ru.lemonapes.dungler

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.lemonapes.dungler.screens.Screen

val topLevelRoutes = listOf(
    Screen.Character,
    Screen.Inventory,
    Screen.Craft,
    Screen.Dungeons
)

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar(
        tonalElevation = 8.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        topLevelRoutes.forEach { topLevelRoute ->
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier
                            .size(64.dp)
                            .padding(6.dp),
                        painter = painterResource(id = topLevelRoute.icon),
                        contentDescription = topLevelRoute.label
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == topLevelRoute.route } == true,
                onClick = {
                    navController.navigate(topLevelRoute) {
                        popUpTo(navController.graph.startDestinationRoute ?: "") {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}