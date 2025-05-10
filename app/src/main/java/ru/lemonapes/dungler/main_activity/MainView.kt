package ru.lemonapes.dungler.main_activity

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import ru.lemonapes.dungler.BottomBar
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.navigation.character.characterNavigation
import ru.lemonapes.dungler.navigation.craft.craftNavigation
import ru.lemonapes.dungler.navigation.dungeon.dungeonNavigation
import ru.lemonapes.dungler.navigation.dungeon_list.dungeonListNavigation
import ru.lemonapes.dungler.navigation.inventory.inventoryNavigation
import ru.lemonapes.dungler.ui.theme.DunglerTheme

@Composable
fun MainView(
    modifier: Modifier = Modifier,
    heroState: HeroState,
    mainActivityState: MainActivityState,
    navController: NavHostController,
    listener: MainViewListener,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (mainActivityState.rootRoute == MainRoute.MAIN) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            if (mainActivityState.rootRoute == MainRoute.MAIN) {
                HeroTopBar(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(16.dp),
                    heroState = heroState
                )
            } else {
                DungeonTopBar(
                    modifier = Modifier,
                    onExitClick = listener.onDungeonExitClick
                )
            }
            MainViewContent(state = mainActivityState, navController = navController)
        }
    }
}

@Composable
private fun MainViewContent(
    modifier: Modifier = Modifier,
    state: MainActivityState,
    navController: NavHostController,
) {
    when (state.rootRoute) {
        MainRoute.MAIN ->
            NavHost(
                navController = navController,
                startDestination = state.mainStartRoute,
                enterTransition = { fadeIn(animationSpec = tween(200)) },
                exitTransition = { fadeOut(animationSpec = tween(200)) },
            ) {
                navigation<Screens.CharacterRoot>(startDestination = Screens.Equipment) {
                    characterNavigation()
                }
                navigation<Screens.CraftRoot>(startDestination = Screens.Craft) {
                    craftNavigation()
                }
                navigation<Screens.InventoryRoot>(startDestination = Screens.Inventory) {
                    inventoryNavigation()
                }
                navigation<Screens.DungeonListRoot>(startDestination = Screens.DungeonList) {
                    dungeonListNavigation()
                }
            }

        MainRoute.DUNGEON -> NavHost(
            navController = navController,
            startDestination = Screens.DungeonRoot,
            enterTransition = { fadeIn(animationSpec = tween(200)) },
            exitTransition = { fadeOut(animationSpec = tween(200)) },
        ) {
            navigation<Screens.DungeonRoot>(startDestination = Screens.Dungeon) {
                dungeonNavigation()
            }
        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    DunglerTheme(darkTheme = true) {
        MainView(
            heroState = HeroState.MOCK,
            mainActivityState = MainActivityState.EMPTY,
            navController = rememberNavController(),
            listener = MainViewListener.EMPTY
        )
    }
}