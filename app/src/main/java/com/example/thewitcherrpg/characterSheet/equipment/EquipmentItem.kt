package com.example.thewitcherrpg.characterSheet.equipment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EquipmentItem(var name: String,
                         var stoppingPower: Int,
                         var availability: String,
                         var armorEnhancement: String,
                         var effect: String,
                         var encumbranceValue: Int,
                         var weight: Float,
                         var cost: Int,
                         val equipmentType: EquipmentTypes) : Parcelable
