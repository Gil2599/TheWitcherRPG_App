package com.example.thewitcherrpg.core.domain.repository

import com.example.thewitcherrpg.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacters(): Flow<List<Character>>

    suspend fun addChar(character: Character)

    suspend fun getCharacterById(id: Int): Character?

    suspend fun deleteChar(character: Character)

    suspend fun updateChar(character: Character)
}