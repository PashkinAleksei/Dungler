package ru.lemonapes.dungler.navigation.character.item_comparison_dialog

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import ru.lemonapes.dungler.domain_models.Food
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.parent_view_model.State

sealed interface DialogEquipmentState {

    data class GearInventory(
        val equippedGear: Gear? = null,
        val inventoryList: ImmutableList<Gear>,
        override val error: Throwable? = null,
        override val isLoading: Boolean = false,
    ) : DialogEquipmentState, State

    data class GearShowEquipped(
        val equippedGear: Gear,
    ) : DialogEquipmentState

    data class GearComparison(
        val equippedGear: Gear? = null,
        val gearToCompare: Gear,
        val inventoryList: ImmutableList<Gear>,
    ) : DialogEquipmentState

    data class FoodInventory(
        val equippedFood: Food? = null,
        val inventoryList: ImmutableList<Food> = persistentListOf(),
        override val error: Throwable? = null,
        override val isLoading: Boolean = false,
    ) : DialogEquipmentState, State

    data class FoodShowEquipped(
        val equippedFood: Food,
    ) : DialogEquipmentState

    data class FoodComparison(
        val equippedFood: Food? = null,
        val foodToCompare: Food,
        val inventoryList: ImmutableList<Food>,
    ) : DialogEquipmentState

    companion object {
        val GEAR_DESCRIPTION_MOCK
            get() = GearShowEquipped(
                equippedGear = Gear.MOCK_1
            )

        val GEAR_INVENTORY_MOCK_SMALL
            get() = GearInventory(
                equippedGear = Gear.MOCK_1,
                inventoryList = persistentListOf(Gear.MOCK_1)
            )

        val GEAR_INVENTORY_MOCK_BIG: DialogEquipmentState
            get() {
                val gearList = arrayListOf<Gear>()
                for (i in 0..18) {
                    gearList.add(Gear.MOCK_1)
                }
                return GearInventory(
                    equippedGear = Gear.MOCK_1,
                    inventoryList = gearList.toPersistentList()
                )
            }

        val GEAR_COMPARISON_MOCK: DialogEquipmentState
            get() {
                return GearComparison(
                    equippedGear = Gear.MOCK_1,
                    gearToCompare = Gear.MOCK_2,
                    inventoryList = persistentListOf(),
                )
            }

        val FOOD_DESCRIPTION_MOCK
            get() = FoodShowEquipped(
                equippedFood = Food.MOCK_1
            )

        val FOOD_INVENTORY_MOCK_SMALL
            get() = FoodInventory(
                inventoryList = persistentListOf(Food.MOCK_1)
            )

        val FOOD_INVENTORY_MOCK_BIG: DialogEquipmentState
            get() {
                val foodList = arrayListOf<Food>()
                for (i in 0..18) {
                    foodList.add(Food.MOCK_1)
                }
                return FoodInventory(
                    inventoryList = foodList.toPersistentList()
                )
            }

        val FOOD_COMPARISON_MOCK: DialogEquipmentState
            get() {
                return FoodComparison(
                    equippedFood = Food.MOCK_1,
                    foodToCompare = Food.MOCK_2,
                    inventoryList = persistentListOf(Food.MOCK_1),
                )
            }
    }
}