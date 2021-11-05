package com.example.thewitcherrpg.feature_character_sheet.presentation.magic

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MagicItem(
    var type: MagicType,
    var name: String,
    var staminaCost: Int?,
    var description: String,
    var range: String? = null,
    var duration: String? = null,
    var defense: String? = null,
    var element: String? = null,
    var difficulty: Int? = null,
    var preparation: String? = null,
    var components: String? = null,
    var requirementToLift: String? = null,
    var danger: String? = null
) : Parcelable