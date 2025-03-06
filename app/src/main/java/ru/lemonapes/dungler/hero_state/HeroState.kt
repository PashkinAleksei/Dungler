package ru.lemonapes.dungler.hero_state

data class HeroState(
    val level: Int? = null,
    val health: Int? = null,
    val totalHealth: Int? = null,
    val experience: Int? = null,
    val totalExperience: Int? = null,
    val isLoading: Boolean = true,
    val dungeonState: DungeonState? = null,
) {
    companion object {
        val EMPTY
            get() = HeroState()
        val MOCK
            get() = HeroState(
                level = 3,
                health = 123,
                totalHealth = 200,
                experience = 140,
                totalExperience = 250,
                isLoading = false,
                dungeonState = null,
            )
    }
}

data class DungeonState(
    val hallNumber: Int? = null,
    val districtStringId: String? = null,
    val dungeonStringId: String? = null,
    //val Enemies: List<Enemy> = emptyList(),
    //val Actions: List<Actions> = emptyList(),
)