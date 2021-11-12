package com.example.thewitcherrpg.feature_character_sheet.domain.use_cases

import com.example.thewitcherrpg.core.Resource
import com.example.thewitcherrpg.core.domain.model.Character
import com.example.thewitcherrpg.core.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class DeleteCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
){
    operator fun invoke(characterId: Int): Flow<Resource<String>> = flow{
        emit(Resource.Loading())

        try {
            repository.getCharacterById(characterId).collect {
                repository.deleteChar(it)
            }
            emit(Resource.Success("Character Deleted Successfully"))
        } catch (ex: Exception){
            emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}