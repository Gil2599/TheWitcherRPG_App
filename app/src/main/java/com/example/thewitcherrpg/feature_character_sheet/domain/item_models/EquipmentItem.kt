package com.example.thewitcherrpg.feature_character_sheet.domain.item_models

import android.os.Parcelable
import com.example.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class EquipmentItem(
    var name: String,
    var stoppingPower: Int,
    var currentStoppingPower: Int,
    var currentRArmSP: Int = stoppingPower,
    var currentLArmSP: Int = stoppingPower,
    var currentRLegSP: Int = stoppingPower,
    var currentLLegSP: Int = stoppingPower,
    var availability: String,
    var armorEnhancement: String,
    var effect: String,
    var encumbranceValue: Int,
    var weight: Float,
    var cost: Int,
    val equipmentType: EquipmentTypes,
    val uniqueID: UUID = UUID.randomUUID()
) : Parcelable {
    constructor() : this(
        "",
        0,
        0,
        0,
        0,
        0,
        0,
        "",
        "",
        "",
        0,
        0F,
        0,
        EquipmentTypes.MISC
    )
}
