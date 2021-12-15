package com.witcher.thewitcherrpg.feature_character_sheet.domain.use_cases

import android.util.Log
import com.witcher.thewitcherrpg.core.domain.model.Character
import com.witcher.thewitcherrpg.core.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject

class CheckIfDataChangedUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(id: Int, viewModelCharacter: Character): Boolean {

        var dataChanged = false
            runBlocking {
                try {
                    repository.getCharacterById(id).take(1).collect {
                        dataChanged = !checkIfEquals(viewModelCharacter, databaseCharacter = it)
                    }

            } catch (ex: Exception){
                Log.e("Error" ,"An unexpected error occurred")
            }
        }
        return dataChanged
    }

    private fun checkIfEquals(viewModelCharacter: Character, databaseCharacter: Character): Boolean {
        return viewModelCharacter == databaseCharacter
    }
}