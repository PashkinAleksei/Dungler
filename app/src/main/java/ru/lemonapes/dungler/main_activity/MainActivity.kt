package ru.lemonapes.dungler.main_activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.lemonapes.dungler.navigation.BottomBarDelegate
import ru.lemonapes.dungler.navigation.HeroTopBarDelegate
import ru.lemonapes.dungler.navigation.LocalBottomBarDelegate
import ru.lemonapes.dungler.navigation.LocalHeroTopBarDelegate
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
        hideSystemUI()

        setContent {
            val state = mainViewModel.observeState().collectAsState().value
            val heroState = heroStateRepository.heroStateFlow.collectAsState().value

            val bottomBarVisible: MutableState<Boolean> = remember { mutableStateOf(true) }
            val heroTopBarVisible: MutableState<Boolean> = remember { mutableStateOf(true) }
            val bottomBarDelegate: BottomBarDelegate = remember { BottomBarDelegate(bottomBarVisible) }
            val heroTopBarDelegate: HeroTopBarDelegate = remember { HeroTopBarDelegate(heroTopBarVisible) }

            DunglerTheme(darkTheme = true) {
                CompositionLocalProvider(
                    LocalBottomBarDelegate provides bottomBarDelegate,
                    LocalHeroTopBarDelegate provides heroTopBarDelegate,
                ) {
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
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController?.let { controller ->
                controller.hide(WindowInsets.Type.systemBars())
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
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