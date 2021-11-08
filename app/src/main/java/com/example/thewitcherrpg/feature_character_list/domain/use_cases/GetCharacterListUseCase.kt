package com.example.thewitcherrpg.feature_character_list.domain.use_cases

import com.example.thewitcherrpg.feature_character_list.domain.model.Character
import com.example.thewitcherrpg.feature_character_list.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetCharacterListUseCase(
    private val repository: CharacterRepository
) {

    operator fun invoke(): Flow<List<Character>>{
        return repository.getCharacters()
    }

}