package com.example.thewitcherrpg.feature_character_sheet.domain.use_cases.equipment

import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.TheWitcherTRPGApp
import com.example.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import com.example.thewitcherrpg.feature_character_sheet.domain.models.ArmorSet
import com.example.thewitcherrpg.feature_character_sheet.domain.models.EquipmentItem
import javax.inject.Inject

class GetArmorSetListUseCase @Inject constructor() {

    operator fun invoke(source: Int): ArrayList<ArmorSet> {

        val equipmentStringArray =
            TheWitcherTRPGApp.getContext()!!.resources!!.getStringArray(source)

        return when (source) {

            R.array.armor_set_light_data -> getEquipmentListInfo(
                equipmentStringArray,
                EquipmentTypes.LIGHT_ARMOR_SET
            )
            R.array.armor_set_medium_data-> getEquipmentListInfo(
                equipmentStringArray,
                EquipmentTypes.MEDIUM_ARMOR_SET
            )
            R.array.armor_set_heavy_data -> getEquipmentListInfo(
                equipmentStringArray,
                EquipmentTypes.HEAVY_ARMOR_SET
            )

            else -> arrayListOf()
        }

    }

    private fun getEquipmentListInfo(
        itemList: Array<String>,
        type: EquipmentTypes
    ): ArrayList<ArmorSet> {

        val equipmentArray: ArrayList<ArmorSet> = arrayListOf()

        for (item in itemList) {
            val pair = item.split(":").toTypedArray()
            val armorName = pair[0]
            val stoppingPower = pair[1].toInt()
            val availability = pair[2]
            val armorEnhancement = pair[3]
            val effect = ArrayList(pair[4].split(","))
            val cover = ArrayList(pair[5].split(","))
            val encValue = pair[6].toInt()
            val weight = pair[7].toFloat()
            val price = pair[8].toInt()

            equipmentArray.add(
                ArmorSet(
                    name = armorName,
                    stoppingPower = stoppingPower,
                    cover = cover,
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