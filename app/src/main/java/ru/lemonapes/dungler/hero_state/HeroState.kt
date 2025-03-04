package ru.lemonapes.dungler.hero_state

data class HeroState(
    val level: Int? = null,
    val health: Int? = null,
    val totalHealth: Int? = null,
    val experience: Int? = null,
    val isLoading: Boolean = true,
    val dungeonState: DungeonState? = null,
) {
    companion object {
        val EMPTY
            get() = HeroState()
    }
}

data class DungeonState(
    val hallNumber: Int? = null,
    val districtStringId: String? = null,
    val dungeonStringId: String? = null,
    //val Enemies: List<Enemy> = emptyList(),
    //val Actions: List<Actions> = emptyList(),
)