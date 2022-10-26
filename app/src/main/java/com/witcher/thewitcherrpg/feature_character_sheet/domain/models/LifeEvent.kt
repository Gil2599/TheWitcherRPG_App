package com.witcher.thewitcherrpg.feature_character_sheet.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
data class LifeEvent(
    var title: String,
    var description: String,
    var age: Int,
    val uniqueID: UUID = UUID.randomUUID()
) : Parcelable, Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }
}