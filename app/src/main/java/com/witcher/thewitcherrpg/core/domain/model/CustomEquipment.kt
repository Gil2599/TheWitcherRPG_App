package com.witcher.thewitcherrpg.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.witcher.thewitcherrpg.core.Constants
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.*
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Entity(tableName = "custom_equipment")
data class CustomEquipment(
    @PrimaryKey
    val equipment: @RawValue Equipment
) : Parcelable

class MyTypeEquipmentConverters {

    @TypeConverter
    fun fromStringToItem(item: String?): Equipment?{
        val itemType =object : TypeToken<Equipment>(){}.type
        return Gson().fromJson(item, itemType)
    }

    @TypeConverter
    fun fromItem(item: Equipment?): String{
        return Gson().toJson(item)
    }

    @TypeConverter
    fun fromItemArrayListToJson(list: ArrayList<Equipment?>): String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToItemArrayList(list: String): ArrayList<Equipment>{
        val itemType =object : TypeToken<ArrayList<Equipment>>(){}.type
        return Gson().fromJson(list, itemType)
    }
}