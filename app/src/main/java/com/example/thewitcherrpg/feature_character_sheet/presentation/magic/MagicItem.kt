package com.example.thewitcherrpg.feature_character_sheet.presentation.magic

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MagicItem(
    var name: String,
    var staCost: Int,
    var effect: String,
    var range: String,
    var duration: String,
    var defense: String,
    var element: String,
    var magicType: MagicTypes

) : Parcelable {
}