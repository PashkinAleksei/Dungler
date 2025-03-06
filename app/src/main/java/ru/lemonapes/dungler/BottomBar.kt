package ru.lemonapes.dungler

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.lemonapes.dungler.navigation.Screens

data class TopLevelRoute<T : Any>(val route: T, val icon: Int)

val topLevelRoutes = listOf(
    TopLevelRoute(Screens.Character, R.drawable.ic_helm),
    TopLevelRoute(Screens.Inventory, R.drawable.ic_backpack),
    TopLevelRoute(Screens.Craft, R.drawable.ic_blacksmith),
    TopLevelRoute(Screens.DungeonList, R.drawable.ic_cave),
)

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar(
        tonalElevation = 8.dp,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        topLevelRoutes.forEach { topLevelRoute ->
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier
                            .size(60.dp)
                            .padding(vertical = 2.dp),
                        painter = painterResource(id = topLevelRoute.icon),
                        contentDescription = topLevelRoute.route.label,
                    )
                },
                selected = currentDestination?.hierarchy?.any {
                    it.hasRoute(topLevelRoute.route::class)
                } == true,
                onClick = {
                    navController.navigate(topLevelRoute.route) {
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