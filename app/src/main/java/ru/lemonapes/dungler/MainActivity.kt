package ru.lemonapes.dungler

import android.annotation.SuppressLint
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
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.navigation.Screens.Dungeons
import ru.lemonapes.dungler.navigation.Screens.Inventory
import ru.lemonapes.dungler.navigation.character.characterNavigation
import ru.lemonapes.dungler.navigation.craft.craftNavigation
import ru.lemonapes.dungler.navigation.dungeons.DungeonsScreen
import ru.lemonapes.dungler.navigation.inventory.InventoryScreen
import ru.lemonapes.dungler.ui.theme.DunglerTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DunglerTheme(darkTheme = true) {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    MainView()
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
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Craft,
            modifier = Modifier.padding(innerPadding)
        ) {
            characterNavigation(navController)
            craftNavigation(navController)
            composable<Inventory> { InventoryScreen() }
            composable<Dungeons> { DungeonsScreen() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DunglerTheme(darkTheme = true) {
        MainView()
    }
}