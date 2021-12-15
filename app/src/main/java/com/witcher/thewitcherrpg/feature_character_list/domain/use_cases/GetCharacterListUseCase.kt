package com.witcher.thewitcherrpg.feature_character_list.domain.use_cases

import com.witcher.thewitcherrpg.core.domain.model.Character
import com.witcher.thewitcherrpg.core.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterListUseCase @Inject constructor(
    private val repository: CharacterRepository
) {

    operator fun invoke(): Flow<List<Character>>{
        return repository.getCharacters()
    }
}