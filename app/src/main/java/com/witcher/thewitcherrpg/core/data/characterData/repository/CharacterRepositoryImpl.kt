package com.witcher.thewitcherrpg.core.data.characterData.repository

import com.witcher.thewitcherrpg.core.data.characterData.data_source.CharacterDao
import com.witcher.thewitcherrpg.core.domain.model.Character
import com.witcher.thewitcherrpg.core.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val charDao: CharacterDao
    ): CharacterRepository {

    override fun getCharacters(): Flow<List<Character>> {
        return charDao.readAllData()
    }

    override suspend fun addChar(character: Character){
        charDao.addChar(character)
    }

    override fun getCharacterById(id: Int): Flow<Character>{
        return charDao.getCharacterById(id)
    }

    override suspend fun deleteChar(character: Character){
        charDao.deleteChar(character)
    }

    override suspend fun updateChar(character: Character){
        charDao.updateChar(character)
    }
}
