package ru.lemonapes.dungler.main_activity

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
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
    content: @Composable () -> Unit,
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
            content()
        }
    }
}

@Composable
fun MainViewContent(
    modifier: Modifier = Modifier,
    state: MainActivityState,
    navController: NavHostController,
) {
    when (state.rootRoute) {
        MainRoute.MAIN ->
            NavHost(
                navController = navController,
                startDestination = Screens.Character,
                enterTransition = { fadeIn(animationSpec = tween(200)) },
                exitTransition = { fadeOut(animationSpec = tween(200)) },
            ) {
                characterNavigation()
                craftNavigation()
                inventoryNavigation()
                dungeonListNavigation()
            }

        MainRoute.DUNGEON -> NavHost(
            navController = navController,
            startDestination = Screens.Dungeon,
            enterTransition = { fadeIn(animationSpec = tween(200)) },
            exitTransition = { fadeOut(animationSpec = tween(200)) },
        ) {
            dungeonNavigation()
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
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            )
        }
    }
} 