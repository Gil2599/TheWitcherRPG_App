package com.witcher.thewitcherrpg.feature_character_sheet.domain.use_cases.equipment

import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.ArmorSet
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.EquipmentItem
import javax.inject.Inject

class GetArmorFromArmorSetUseCase @Inject constructor() {

    operator fun invoke(armorSet: ArmorSet): ArrayList<EquipmentItem> {

        val equipmentItems = arrayListOf<EquipmentItem>()

        for (cover in armorSet.cover) {
            when (cover) {
                "head" -> {
                    equipmentItems.add(
                        EquipmentItem(
                            name = armorSet.name,
                            stoppingPower = armorSet.stoppingPower,
                            availability = armorSet.availability,
                            armorEnhancement = armorSet.armorEnhancement,
                            encumbranceValue = armorSet.encumbranceValue,
                            effect = armorSet.effect.toString(),
                            weight = 0F,
                            cost = armorSet.cost,
                            isRelic = armorSet.isRelic,
                            equipmentType = when (armorSet.equipmentType) {
                                EquipmentTypes.MEDIUM_ARMOR_SET -> EquipmentTypes.MEDIUM_HEAD
                                EquipmentTypes.HEAVY_ARMOR_SET -> EquipmentTypes.HEAVY_HEAD
                                else -> {
                                    EquipmentTypes.LIGHT_HEAD
                                }
                            }
                        )
                    )
                }
                "chest" -> {
                    equipmentItems.add(
                        EquipmentItem(
                            name = armorSet.name,
                            stoppingPower = armorSet.stoppingPower,
                            availability = armorSet.availability,
                            armorEnhancement = armorSet.armorEnhancement,
                            encumbranceValue = armorSet.encumbranceValue,
                            effect = armorSet.effect.toString(),
                            weight = armorSet.weight,
                            cost = armorSet.cost,
                            isRelic = armorSet.isRelic,
                            equipmentType = when (armorSet.equipmentType) {
                                EquipmentTypes.MEDIUM_ARMOR_SET -> EquipmentTypes.MEDIUM_CHEST
                                EquipmentTypes.HEAVY_ARMOR_SET -> EquipmentTypes.HEAVY_CHEST
                                else -> {
                                    EquipmentTypes.LIGHT_CHEST
                                }
                            }
                        )
                    )
                }
                "legs" -> {
                    equipmentItems.add(
                        EquipmentItem(
                            name = armorSet.name,
                            stoppingPower = armorSet.stoppingPower,
                            availability = armorSet.availability,
                            armorEnhancement = armorSet.armorEnhancement,
                            encumbranceValue = armorSet.encumbranceValue,
                            effect = armorSet.effect.toString(),
                            weight = 0F,
                            cost = armorSet.cost,
                            isRelic = armorSet.isRelic,
                            equipmentType = when (armorSet.equipmentType) {
                                EquipmentTypes.MEDIUM_ARMOR_SET -> EquipmentTypes.MEDIUM_LEGS
                                EquipmentTypes.HEAVY_ARMOR_SET -> EquipmentTypes.HEAVY_LEGS
                                else -> {
                                    EquipmentTypes.LIGHT_LEGS
                                }
                            }
                        )
                    )
                }
            }
        }
        return equipmentItems
    }
}