package com.witcher.thewitcherrpg.feature_character_sheet.domain.models

import android.os.Parcelable
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.MagicType
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class MagicItem(
    var type: MagicType,
    var name: String,
    var staminaCost: Int = 0,
    var description: String,
    var range: String = "",
    var duration: String = "",
    var defense: String = "",
    var element: String = "",
    var difficulty: Int = 0,
    var preparation: String = "",
    var components: String = "",
    var requirementToLift: String = "",
    var danger: String = "",
    var sideEffect: String = "",
    var isCustom: Boolean = false,
    var isTomesOfChaosDLC: Boolean = false
) : Parcelable, Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }
}