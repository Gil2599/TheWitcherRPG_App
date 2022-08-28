package com.witcher.thewitcherrpg.core.data.customAttributesData.repository

import com.witcher.thewitcherrpg.core.data.characterData.data_source.CharacterDao
import com.witcher.thewitcherrpg.core.data.customAttributesData.data_source.CustomAttributesDao
import com.witcher.thewitcherrpg.core.domain.model.Character
import com.witcher.thewitcherrpg.core.domain.model.CustomEquipment
import com.witcher.thewitcherrpg.core.domain.model.CustomMagic
import com.witcher.thewitcherrpg.core.domain.repository.CharacterRepository
import com.witcher.thewitcherrpg.core.domain.repository.CustomAttributesRepository
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.Equipment
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.MagicItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomAttributesRepositoryImpl @Inject constructor(
    private val customAttributesDao: CustomAttributesDao
): CustomAttributesRepository {

    override fun getMagic(): Flow<List<CustomMagic>> {
        return customAttributesDao.readAllMagic()
    }

    override fun getEquipment(): Flow<List<Equipment>> {
        return customAttributesDao.readAllEquipment()
    }

    override suspend fun addMagic(magic: CustomMagic) {
        return customAttributesDao.addMagic(magic)
    }

    override suspend fun addEquipment(equipment: CustomEquipment) {
        return customAttributesDao.addEquipment(equipment)
    }

    override suspend fun deleteMagic(magic: CustomMagic) {
        return customAttributesDao.deleteMagic(magic)
    }


}