package com.witcher.thewitcherrpg.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.EquipmentItem
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Entity(tableName = "custom_equipment")
data class CustomEquipment(
    @PrimaryKey
    val equipment: @RawValue EquipmentItem
) : Parcelable

class MyTypeEquipmentConverters {

    @TypeConverter
    fun fromStringToItem(item: String?): EquipmentItem?{
        val itemType =object : TypeToken<EquipmentItem>(){}.type
        return Gson().fromJson(item, itemType)
    }

    @TypeConverter
    fun fromItem(item: EquipmentItem?): String{
        return Gson().toJson(item)
    }

    @TypeConverter
    fun fromItemArrayListToJson(list: ArrayList<EquipmentItem?>): String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToItemArrayList(list: String): ArrayList<EquipmentItem>{
        val itemType =object : TypeToken<ArrayList<EquipmentItem>>(){}.type
        return Gson().fromJson(list, itemType)
    }
}