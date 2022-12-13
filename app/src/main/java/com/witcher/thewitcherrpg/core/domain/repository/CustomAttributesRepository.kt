package com.witcher.thewitcherrpg.core.domain.repository

import com.witcher.thewitcherrpg.core.domain.model.CustomEquipment
import com.witcher.thewitcherrpg.core.domain.model.CustomMagic
import com.witcher.thewitcherrpg.core.domain.model.CustomWeapon
import kotlinx.coroutines.flow.Flow

interface CustomAttributesRepository {

    fun getMagic(): Flow<List<CustomMagic>>

    fun getWeapon(): Flow<List<CustomWeapon>>

    suspend fun getEquipment(): Flow<List<CustomEquipment>>

    suspend fun addMagic(magic: CustomMagic)

    suspend fun addWeapon(equipment: CustomWeapon)

    suspend fun addEquipment(equipment: CustomEquipment)

    suspend fun deleteMagic(magicName: String)

    suspend fun deleteWeapon(weapon: CustomWeapon)

    suspend fun deleteEquipment(equipment: CustomEquipment)

}