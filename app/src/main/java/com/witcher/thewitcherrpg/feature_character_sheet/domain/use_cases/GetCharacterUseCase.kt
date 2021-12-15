package com.witcher.thewitcherrpg.feature_character_sheet.domain.use_cases

import com.witcher.thewitcherrpg.core.Resource
import com.witcher.thewitcherrpg.core.domain.model.Character
import com.witcher.thewitcherrpg.core.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {

    operator fun invoke(id: Int): Flow<Resource<Character>> = flow{
        emit(Resource.Loading())

        try {
            repository.getCharacterById(id).collect {
                emit(Resource.Success(it))
            }
        } catch (ex: Exception){
            //emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}