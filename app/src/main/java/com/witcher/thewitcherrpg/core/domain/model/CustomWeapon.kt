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
@Entity(tableName = "custom_weapon")
data class CustomWeapon(
    @PrimaryKey
    val equipment: @RawValue WeaponItem
) : Parcelable

class MyTypeWeaponConverters {

    @TypeConverter
    fun fromStringToItem(item: String?): WeaponItem?{
        val itemType =object : TypeToken<WeaponItem>(){}.type
        return Gson().fromJson(item, itemType)
    }

    @TypeConverter
    fun fromItem(item: WeaponItem?): String{
        return Gson().toJson(item)
    }

    @TypeConverter
    fun fromItemArrayListToJson(list: ArrayList<WeaponItem?>): String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToItemArrayList(list: String): ArrayList<WeaponItem>{
        val itemType =object : TypeToken<ArrayList<WeaponItem>>(){}.type
        return Gson().fromJson(list, itemType)
    }
}