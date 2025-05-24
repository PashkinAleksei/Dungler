package ru.lemonapes.dungler.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

inline fun <reified T : Any> NavGraphBuilder.composableWithBars(
    deepLinks: List<NavDeepLink> = emptyList(),
    bottomBarVisible: Boolean = true,
    heroTopBarVisible: Boolean = true,
    noinline content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable<T>(
        deepLinks = deepLinks,
        content = { navBackStackEntry ->
            content(navBackStackEntry)
            val bottomBarVisibleState = LocalBottomBarDelegate.current.bottomBarVisible
            val heroTopBarVisibleState = LocalHeroTopBarDelegate.current.heroTopBarVisible
            LaunchedEffect(key1 = Unit) {
                bottomBarVisibleState.value = bottomBarVisible
                heroTopBarVisibleState.value = heroTopBarVisible
            }
        },
    )
}