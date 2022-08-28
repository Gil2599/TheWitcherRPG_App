package com.witcher.thewitcherrpg.core.data.customAttributesData.data_source

import androidx.room.*
import com.witcher.thewitcherrpg.core.domain.model.Character
import com.witcher.thewitcherrpg.core.domain.model.CustomEquipment
import com.witcher.thewitcherrpg.core.domain.model.CustomMagic
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.Equipment
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.MagicItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomAttributesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMagic(magic: CustomMagic)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEquipment(equipment: CustomEquipment)

    @Query("SELECT * FROM custom_magic")
    fun readAllMagic(): Flow<List<CustomMagic>>

    @Query("SELECT * FROM custom_equipment")
    fun readAllEquipment(): Flow<List<Equipment>>

    @Delete
    suspend fun deleteMagic(magic: CustomMagic)
//
//    @Update
//    suspend fun updateChar(character: Character)

}