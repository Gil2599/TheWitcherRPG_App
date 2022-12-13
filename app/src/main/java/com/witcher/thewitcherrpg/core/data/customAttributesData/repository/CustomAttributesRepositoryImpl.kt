package com.witcher.thewitcherrpg.core.data.customAttributesData.repository

import com.witcher.thewitcherrpg.core.data.customAttributesData.data_source.CustomAttributesDao
import com.witcher.thewitcherrpg.core.domain.model.CustomEquipment
import com.witcher.thewitcherrpg.core.domain.model.CustomMagic
import com.witcher.thewitcherrpg.core.domain.model.CustomWeapon
import com.witcher.thewitcherrpg.core.domain.repository.CustomAttributesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomAttributesRepositoryImpl @Inject constructor(
    private val customAttributesDao: CustomAttributesDao
): CustomAttributesRepository {

    override fun getMagic(): Flow<List<CustomMagic>> {
        return customAttributesDao.readAllMagic()
    }

    override fun getWeapon(): Flow<List<CustomWeapon>> {
        return customAttributesDao.readAllWeapons()
    }

    override suspend fun getEquipment(): Flow<List<CustomEquipment>> {
        return customAttributesDao.readAllEquipment()
    }

    override suspend fun addMagic(magic: CustomMagic) {
        return customAttributesDao.addMagic(magic)
    }

    override suspend fun addWeapon(equipment: CustomWeapon) {
        return customAttributesDao.addWeapon(equipment)
    }

    override suspend fun addEquipment(equipment: CustomEquipment) {
        return customAttributesDao.addEquipment(equipment)
    }

    override suspend fun deleteMagic(magicName: String) {
        return customAttributesDao.deleteMagic(magicName)
    }

    override suspend fun deleteWeapon(weapon: CustomWeapon) {
        return customAttributesDao.deleteWeapon(weapon)
    }

    override suspend fun deleteEquipment(equipment: CustomEquipment) {
        return customAttributesDao.deleteEquipment(equipment)
    }

}