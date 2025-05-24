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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import ru.lemonapes.dungler.BottomBar
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.navigation.RootScreens
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.navigation.character.equipment.characterNavigation
import ru.lemonapes.dungler.navigation.character.skills.skill_list.skillsListNavigation
import ru.lemonapes.dungler.navigation.character.skills.skills_equipment.skillsEquipmentNavigation
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
                        .fillMaxWidth()
                        .padding(16.dp),
                    heroState = heroState,
                    topBarListener = TopBarListener(
                        toEquipment = { navController.navigate(Screens.Equipment) },
                        toSkills = { navController.navigate(Screens.EquipmentSkills) }
                    ),
                    activeButton = navController.getTopBarActiveButton()
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
private fun NavController.getTopBarActiveButton(): TopBarButtonActive? {
    val navBackStackEntry by currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringAfterLast('.')
    val currentRouteLastPart = remember(currentRoute) { currentRoute?.substringAfterLast('.') }
    return when (currentRouteLastPart) {
        Screens.Equipment.toString() -> {
            TopBarButtonActive.EQUIPMENT
        }

        Screens.EquipmentSkills.toString() -> {
            TopBarButtonActive.SKILLS
        }

        else -> {
            null
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
                navigation<RootScreens.CharacterRoot>(startDestination = Screens.Equipment) {
                    characterNavigation()
                    skillsEquipmentNavigation(navController)
                    skillsListNavigation(navController)
                }
                navigation<RootScreens.CraftRoot>(startDestination = Screens.Craft) {
                    craftNavigation()
                }
                navigation<RootScreens.InventoryRoot>(startDestination = Screens.Inventory) {
                    inventoryNavigation()
                }
                navigation<RootScreens.DungeonListRoot>(startDestination = Screens.DungeonList) {
                    dungeonListNavigation()
                }
            }

        MainRoute.DUNGEON -> NavHost(
            navController = navController,
            startDestination = RootScreens.DungeonRoot,
            enterTransition = { fadeIn(animationSpec = tween(200)) },
            exitTransition = { fadeOut(animationSpec = tween(200)) },
        ) {
            navigation<RootScreens.DungeonRoot>(startDestination = Screens.Dungeon) {
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