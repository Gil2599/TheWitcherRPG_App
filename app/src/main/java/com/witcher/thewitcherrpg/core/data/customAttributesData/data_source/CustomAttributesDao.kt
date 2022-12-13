package com.witcher.thewitcherrpg.core.data.customAttributesData.data_source

import androidx.room.*
import com.witcher.thewitcherrpg.core.domain.model.CustomEquipment
import com.witcher.thewitcherrpg.core.domain.model.CustomMagic
import com.witcher.thewitcherrpg.core.domain.model.CustomWeapon
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomAttributesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMagic(magic: CustomMagic)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeapon(weapon: CustomWeapon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEquipment(equipment: CustomEquipment)

    @Query("SELECT * FROM custom_magic")
    fun readAllMagic(): Flow<List<CustomMagic>>

    @Query("SELECT * FROM custom_weapon")
    fun readAllWeapons(): Flow<List<CustomWeapon>>

    @Query("SELECT * FROM custom_equipment")
    fun readAllEquipment(): Flow<List<CustomEquipment>>

    @Query("DELETE FROM custom_magic WHERE ',' || replace(replace(replace(magicItem, '{', ''), '}', ''), ' ', '') || ',' like '%,\"name\":\"' || :magicName ||'\",%'")
    suspend fun deleteMagic(magicName: String)

    @Delete
    suspend fun deleteWeapon(weapon: CustomWeapon)

    @Delete
    suspend fun deleteEquipment(equipment: CustomEquipment)
}