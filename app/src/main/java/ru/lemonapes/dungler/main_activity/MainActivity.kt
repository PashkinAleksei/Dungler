package ru.lemonapes.dungler.main_activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.lemonapes.dungler.repositories.HeroStateRepository
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
            val heroState = heroStateRepository.heroStateFlow.collectAsState().value
            DunglerTheme(darkTheme = true) {
                val navController = rememberNavController()
                MainView(
                    heroState = heroState,
                    mainActivityState = state,
                    navController = navController,
                    listener = MainViewListener(
                        onDungeonExitClick = mainViewModel::actionDungeonExit
                    )
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.actionStart()
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.actionStop()
    }
}