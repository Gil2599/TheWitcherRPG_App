package com.witcher.thewitcherrpg.feature_character_sheet.domain.models

import android.os.Parcelable
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class ArmorSet(
    var name: String,
    var stoppingPower: Int = 0,
    var cover: ArrayList<String>,
    var availability: String = "",
    var armorEnhancement: String = "",
    var effect: ArrayList<String>,
    var encumbranceValue: Int = 0,
    var weight: Float,
    var cost: Int,
    val equipmentType: EquipmentTypes,
    var isRelic: Boolean = false,
    val uniqueID: UUID = UUID.randomUUID()
) : Parcelable, Serializable, Equipment()