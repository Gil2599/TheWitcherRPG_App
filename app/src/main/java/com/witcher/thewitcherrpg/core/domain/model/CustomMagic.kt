package com.witcher.thewitcherrpg.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.MagicItem
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "custom_magic")
data class CustomMagic(
    @PrimaryKey
    val magicItem: MagicItem
) : Parcelable

class MyTypeMagicConverters {

    @TypeConverter
    fun fromStringToItem(item: String?): MagicItem?{
        val itemType =object : TypeToken<MagicItem>(){}.type
        return Gson().fromJson(item, itemType)
    }

    @TypeConverter
    fun fromItem(item: MagicItem?): String{
        return Gson().toJson(item)
    }

    @TypeConverter
    fun fromItemArrayListToJson(list: ArrayList<MagicItem?>): String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToItemArrayList(list: String): ArrayList<MagicItem>{
        val itemType =object : TypeToken<ArrayList<MagicItem>>(){}.type
        return Gson().fromJson(list, itemType)
    }
}