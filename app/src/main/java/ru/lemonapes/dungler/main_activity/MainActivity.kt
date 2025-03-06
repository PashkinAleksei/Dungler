package ru.lemonapes.dungler.main_activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.lemonapes.dungler.BottomBar
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.navigation.character.characterNavigation
import ru.lemonapes.dungler.navigation.craft.craftNavigation
import ru.lemonapes.dungler.navigation.dungeon.DungeonScreen
import ru.lemonapes.dungler.navigation.dungeon_list.dungeonListNavigation
import ru.lemonapes.dungler.navigation.inventory.inventoryNavigation
import ru.lemonapes.dungler.repositories.HeroStateRepository
import ru.lemonapes.dungler.ui.ActionOnStart
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var heroStateRepository: HeroStateRepository

    private val mainViewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val state = mainViewModel.observeState().collectAsState().value
            ActionOnStart(mainViewModel::actionStart)

            DunglerTheme(darkTheme = true) {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    MainView(state = state)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //HttpClientProvider.close()
    }
}

@Composable
fun MainView(
    modifier: Modifier = Modifier,
    state: MainActivityState,
) {
    val navController = rememberNavController()

    when (state.rootRoute) {
        MainRoute.MAIN ->
            Scaffold(
                modifier = modifier,
                bottomBar = {
                    BottomBar(navController)
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screens.Character,
                    modifier = Modifier.padding(innerPadding),
                    enterTransition = { fadeIn(animationSpec = tween(200)) },
                    exitTransition = { fadeOut(animationSpec = tween(200)) },
                ) {
                    characterNavigation()
                    craftNavigation()
                    inventoryNavigation()
                    dungeonListNavigation()
                }
            }

        MainRoute.DUNGEON ->
            Scaffold(
                modifier = modifier,
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screens.Dungeon,
                    modifier = Modifier.padding(innerPadding),
                    enterTransition = { fadeIn(animationSpec = tween(200)) },
                    exitTransition = { fadeOut(animationSpec = tween(200)) },
                ) {
                    composable<Screens.Dungeon> { DungeonScreen() }
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DunglerTheme(darkTheme = true) {
        MainView(state = MainActivityState.EMPTY)
    }
}