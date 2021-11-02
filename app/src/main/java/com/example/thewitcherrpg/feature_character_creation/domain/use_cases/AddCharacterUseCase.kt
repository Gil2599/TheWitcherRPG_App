package com.example.thewitcherrpg.feature_character_creation.domain.use_cases

import com.example.thewitcherrpg.core.Resource
import com.example.thewitcherrpg.core.data.repository.CharacterRepositoryImpl
import com.example.thewitcherrpg.core.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class AddCharacterUseCase @Inject constructor(
    private val repositoryImpl: CharacterRepositoryImpl,
) {

    operator fun invoke(character: Character): Flow<Resource<Character>> = flow {
        emit(Resource.Loading())

        try {
            repositoryImpl.addChar(character)
            emit(Resource.Success(character))
        } catch (ex: Exception){
            emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred"))
        }


    }
}