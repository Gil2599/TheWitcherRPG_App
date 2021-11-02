package com.example.thewitcherrpg.feature_character_list.domain.use_cases

import com.example.thewitcherrpg.core.domain.model.Character
import com.example.thewitcherrpg.core.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {

    operator fun invoke(id: Int): Flow<Character> {
        return repository.getCharacterById(id)
    }
}