package com.witcher.thewitcherrpg.feature_character_sheet.domain.models

import android.os.Parcelable
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.WeaponTypes
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
data class WeaponItem(
    var name: String,
    var damageType: String,
    var weaponAccuracy: Int,
    var availability: String,
    var damage: String,
    var reliability: Int,
    var currentReliability: Int,
    var hands: Int,
    var rng: String,
    var effect: ArrayList<String>,
    var concealment: String,
    var enhancements: Int,
    var weight: Float,
    var cost: Int,
    var type: WeaponTypes,
    var focus: Int = 0,
    var equipmentNote: String = "",
    var isRelic: Boolean = false,
    var uniqueID: UUID = UUID.randomUUID()
) : Parcelable, Serializable
