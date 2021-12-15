package com.witcher.thewitcherrpg.core.domain.repository

import com.witcher.thewitcherrpg.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacters(): Flow<List<Character>>

    suspend fun addChar(character: Character)

    fun getCharacterById(id: Int): Flow<Character>

    suspend fun deleteChar(character: Character)

    suspend fun updateChar(character: Character)
}