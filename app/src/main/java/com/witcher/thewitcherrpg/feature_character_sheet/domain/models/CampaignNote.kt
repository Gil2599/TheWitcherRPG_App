package com.witcher.thewitcherrpg.feature_character_sheet.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
data class CampaignNote(
    var title: String,
    var description: String,
    var date: String,
    val uniqueID: UUID = UUID.randomUUID()
) : Parcelable, Serializable