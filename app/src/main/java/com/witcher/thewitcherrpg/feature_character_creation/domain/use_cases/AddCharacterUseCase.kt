package com.witcher.thewitcherrpg.feature_character_creation.domain.use_cases

import com.witcher.thewitcherrpg.core.Resource
import com.witcher.thewitcherrpg.core.domain.model.Character
import com.witcher.thewitcherrpg.core.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {

    operator fun invoke(character: Character): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        try {
            repository.addChar(character)
            emit(Resource.Success("Character Saved Successfully"))
        } catch (ex: Exception){
            emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}