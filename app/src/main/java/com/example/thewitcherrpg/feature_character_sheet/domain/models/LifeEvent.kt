package com.example.thewitcherrpg.feature_character_sheet.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class LifeEvent(
    var name: String,
    var description: String,
    var age: Int,
    val uniqueID: UUID = UUID.randomUUID()
) : Parcelable