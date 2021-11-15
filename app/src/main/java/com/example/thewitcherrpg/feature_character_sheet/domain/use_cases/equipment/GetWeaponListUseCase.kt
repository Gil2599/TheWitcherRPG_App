package com.example.thewitcherrpg.feature_character_sheet.domain.use_cases.equipment

import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.TheWitcherTRPGApp
import com.example.thewitcherrpg.feature_character_sheet.domain.item_models.WeaponItem
import com.example.thewitcherrpg.feature_character_sheet.domain.item_types.WeaponTypes
import javax.inject.Inject

class GetWeaponListUseCase @Inject constructor() {

    operator fun invoke(source: Int): ArrayList<WeaponItem> {

        val equipmentStringArray =
            TheWitcherTRPGApp.getContext()!!.resources!!.getStringArray(source)

        return when (source) {

            R.array.swords_data -> getEquipmentListInfo(
                equipmentStringArray,
                WeaponTypes.SWORD
            )
            R.array.small_blades_data -> getEquipmentListInfo(
                equipmentStringArray,
                WeaponTypes.SMALL_BLADE
            )
            R.array.axes_data -> getEquipmentListInfo(
                equipmentStringArray,
                WeaponTypes.AXE
            )
            R.array.bludgeons_data -> getEquipmentListInfo(
                equipmentStringArray,
                WeaponTypes.BLUDGEON
            )
            R.array.pole_arms_data -> getEquipmentListInfo(
                equipmentStringArray,
                WeaponTypes.POLE_ARM
            )
            R.array.staves_data -> getEquipmentListInfo(
                equipmentStringArray,
                WeaponTypes.STAFF
            )
            R.array.thrown_weapons_data -> getEquipmentListInfo(
                equipmentStringArray,
                WeaponTypes.THROWN_WEAPON
            )
            R.array.bows_data -> getEquipmentListInfo(
                equipmentStringArray,
                WeaponTypes.BOW
            )
            R.array.crossbows_data -> getEquipmentListInfo(
                equipmentStringArray,
                WeaponTypes.CROSSBOW
            )

            else -> arrayListOf()
        }

    }

    private fun getEquipmentListInfo(
        itemList: Array<String>,
        type: WeaponTypes
    ): ArrayList<WeaponItem> {

        val equipmentArray: ArrayList<WeaponItem> = arrayListOf()

        for (item in itemList) {
            val pair = item.split(":").toTypedArray()
            val name = pair[0]
            val damageType = pair[1]
            val weaponAccuracy = pair[2].toInt()
            val availability = pair[3]
            val damage = pair[4]
            val reliability = pair[5].toInt()
            val hands = pair[6].toInt()
            val rng = pair[7]
            val effect = ArrayList(pair[8].split(","))
            val concealment = pair[9]
            val enhancement = pair[10].toInt()
            val weight = pair[11].toFloat()
            val price = pair[12].toInt()

            equipmentArray.add(
                WeaponItem(
                    name = name,
                    damageType = damageType,
                    weaponAccuracy = weaponAccuracy,
                    availability = availability,
                    damage = damage,
                    reliability = reliability,
                    currentReliability = reliability,
                    hands = hands,
                    rng = rng,
                    effect = effect,
                    concealment = concealment,
                    enhancements = enhancement,
                    weight = weight,
                    cost = price,
                    type = type
                )
            )
        }
        return equipmentArray
    }
}