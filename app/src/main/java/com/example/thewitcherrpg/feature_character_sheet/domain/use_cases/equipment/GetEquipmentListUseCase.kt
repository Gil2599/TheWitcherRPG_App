package com.example.thewitcherrpg.feature_character_sheet.domain.use_cases.equipment

import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.TheWitcherTRPGApp
import com.example.thewitcherrpg.feature_character_sheet.domain.item_models.EquipmentItem
import com.example.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import javax.inject.Inject

class GetEquipmentListUseCase @Inject constructor() {

    operator fun invoke(source: Int): ArrayList<EquipmentItem> {

        val equipmentStringArray =
            TheWitcherTRPGApp.getContext()!!.resources!!.getStringArray(source)

        return when (source) {

            R.array.head_light_armor_data -> getEquipmentListInfo(
                equipmentStringArray,
                EquipmentTypes.LIGHT_HEAD
            )
            R.array.head_medium_armor_data -> getEquipmentListInfo(
                equipmentStringArray,
                EquipmentTypes.MEDIUM_HEAD
            )
            R.array.head_heavy_armor_data -> getEquipmentListInfo(
                equipmentStringArray,
                EquipmentTypes.HEAVY_HEAD
            )
            R.array.chest_light_armor_data -> getEquipmentListInfo(
                equipmentStringArray,
                EquipmentTypes.LIGHT_CHEST
            )
            R.array.chest_medium_armor_data -> getEquipmentListInfo(
                equipmentStringArray,
                EquipmentTypes.MEDIUM_CHEST
            )
            R.array.chest_heavy_armor_data -> getEquipmentListInfo(
                equipmentStringArray,
                EquipmentTypes.HEAVY_CHEST
            )

            else -> arrayListOf()
        }

    }

    private fun getEquipmentListInfo(
        itemList: Array<String>,
        type: EquipmentTypes
    ): ArrayList<EquipmentItem> {

        val equipmentArray: ArrayList<EquipmentItem> = arrayListOf()

        for (item in itemList) {
            val pair = item.split(":").toTypedArray()
            val armorName = pair[0]
            val stoppingPower = pair[1].toInt()
            val availability = pair[2]
            val armorEnhancement = pair[3]
            val effect = pair[4]
            val encValue = pair[5].toInt()
            val weight = pair[6].toFloat()
            val price = pair[7].toInt()

            equipmentArray.add(
                EquipmentItem(
                    name = armorName,
                    stoppingPower = stoppingPower,
                    currentStoppingPower = stoppingPower,
                    currentRArmSP = stoppingPower,
                    currentLArmSP = stoppingPower,
                    currentRLegSP = stoppingPower,
                    currentLLegSP = stoppingPower,
                    availability = availability,
                    armorEnhancement = armorEnhancement,
                    effect = effect,
                    encumbranceValue = encValue,
                    weight = weight,
                    cost = price,
                    equipmentType = type
                )
            )
        }
        return equipmentArray
    }
}