package com.example.thewitcherrpg.feature_character_list.data.repository

import com.example.thewitcherrpg.feature_character_list.data.data_source.CharacterDao
import com.example.thewitcherrpg.feature_character_list.domain.model.Character
import com.example.thewitcherrpg.feature_character_list.domain.repository.CharacterRepository
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

    override suspend fun getCharacterById(id: Int): Character?{
        return charDao.getCharacterById(id)
    }

    override suspend fun deleteChar(character: Character){
        charDao.deleteChar(character)
    }

    override suspend fun updateChar(character: Character){
        charDao.updateChar(character)
    }

}