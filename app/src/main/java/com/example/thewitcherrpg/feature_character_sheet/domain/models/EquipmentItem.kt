package com.example.thewitcherrpg.feature_character_sheet.domain.models

import android.os.Parcelable
import com.example.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class EquipmentItem(
    var name: String,
    var stoppingPower: Int = 0,
    var currentStoppingPower: Int = stoppingPower,
    var currentRArmSP: Int = stoppingPower,
    var currentLArmSP: Int = stoppingPower,
    var currentRLegSP: Int = stoppingPower,
    var currentLLegSP: Int = stoppingPower,
    var availability: String = "",
    var armorEnhancement: String = "",
    var effect: String,
    var encumbranceValue: Int = 0,
    var weight: Float,
    var cost: Int,
    var quantity: Int = 1,
    var totalCost: Int = cost*quantity,
    val equipmentType: EquipmentTypes,
    var equipmentNote: String = "",
    var isRelic: Boolean = false,
    var uniqueID: UUID = UUID.randomUUID()
) : Parcelable
