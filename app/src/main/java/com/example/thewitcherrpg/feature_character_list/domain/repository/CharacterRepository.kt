package com.example.thewitcherrpg.feature_character_list.domain.repository

import com.example.thewitcherrpg.feature_character_list.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacters(): Flow<List<Character>>

    suspend fun addChar(character: Character)

    suspend fun getCharacterById(id: Int): Character?

    suspend fun deleteChar(character: Character)

    suspend fun updateChar(character: Character)
}