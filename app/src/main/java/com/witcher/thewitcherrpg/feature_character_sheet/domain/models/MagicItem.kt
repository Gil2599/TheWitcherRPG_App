package com.witcher.thewitcherrpg.feature_character_sheet.domain.models

import android.os.Parcelable
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.MagicType
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class MagicItem(
    var type: MagicType,
    var name: String,
    var staminaCost: Int?,
    var description: String,
    var range: String? = null,
    var duration: String? = null,
    var defense: String? = null,
    var element: String? = "",
    var difficulty: Int? = null,
    var preparation: String? = null,
    var components: String? = null,
    var requirementToLift: String? = null,
    var danger: String? = null,
    var isCustom: Boolean = false
) : Parcelable, Serializable