package com.example.thewitcherrpg.feature_character_sheet.domain.item_models

import android.os.Parcelable
import com.example.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class CustomItem(
    var name: String,
    var quantity: Int,
    var description: String,
    var weight: Float,
    var cost: Int,
    val equipmentType: EquipmentTypes,
    val uniqueID: UUID = UUID.randomUUID()
) : Parcelable