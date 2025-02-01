package ru.lemonapes.dungler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.lemonapes.dungler.screens.CharacterScreen
import ru.lemonapes.dungler.screens.CraftScreen
import ru.lemonapes.dungler.screens.DungeonsScreen
import ru.lemonapes.dungler.screens.InventoryScreen
import ru.lemonapes.dungler.screens.Screen.Character
import ru.lemonapes.dungler.screens.Screen.Inventory
import ru.lemonapes.dungler.screens.Screen.Craft
import ru.lemonapes.dungler.screens.Screen.Dungeons
import ru.lemonapes.dungler.ui.theme.DunglerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DunglerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainView(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainView(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Character,
            modifier = modifier.padding(innerPadding)
        ) {
            composable<Character> { CharacterScreen() }
            composable<Inventory> { InventoryScreen() }
            composable<Craft> { CraftScreen() }
            composable<Dungeons> { DungeonsScreen() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DunglerTheme {
        MainView()
    }
}