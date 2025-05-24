package ru.lemonapes.dungler.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf

val LocalBottomBarDelegate = compositionLocalOf<BottomBarDelegate> {
    error("No BottomBarDelegate provided")
}

val LocalHeroTopBarDelegate = compositionLocalOf<HeroTopBarDelegate> {
    error("No HeroTopBarDelegate provided")
}

class BottomBarDelegate(val bottomBarVisible: MutableState<Boolean>)
class HeroTopBarDelegate(val heroTopBarVisible: MutableState<Boolean>)