package com.example.thewitcherrpg.feature_character_sheet.domain.use_cases

import android.util.Log
import com.example.thewitcherrpg.core.Resource
import com.example.thewitcherrpg.core.domain.model.Character
import com.example.thewitcherrpg.core.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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