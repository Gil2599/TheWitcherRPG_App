package com.witcher.thewitcherrpg.core.domain.repository

import com.witcher.thewitcherrpg.core.domain.model.Character
import com.witcher.thewitcherrpg.core.domain.model.CustomEquipment
import com.witcher.thewitcherrpg.core.domain.model.CustomMagic
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.Equipment
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.MagicItem
import kotlinx.coroutines.flow.Flow

interface CustomAttributesRepository {

    fun getMagic(): Flow<List<CustomMagic>>

    fun getEquipment(): Flow<List<Equipment>>

    suspend fun addMagic(magic: CustomMagic)

    suspend fun addEquipment(equipment: CustomEquipment)

    suspend fun deleteMagic(magic: CustomMagic)

    //fun getCharacterById(id: Int): Flow<Character>

    //suspend fun deleteChar(character: Character)

    //suspend fun updateChar(character: Character)
}