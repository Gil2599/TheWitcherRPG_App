package com.witcher.thewitcherrpg.feature_character_list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.witcher.thewitcherrpg.core.Resource
import com.witcher.thewitcherrpg.core.dataStoreRepository.DataStoreRepository
import com.witcher.thewitcherrpg.core.domain.model.Character
import com.witcher.thewitcherrpg.feature_character_creation.domain.use_cases.CharacterCreationUseCases
import com.witcher.thewitcherrpg.feature_character_creation.presentation.CharacterState
import com.witcher.thewitcherrpg.feature_character_list.domain.use_cases.GetCharacterListUseCase
import com.witcher.thewitcherrpg.feature_character_sheet.domain.use_cases.character_information.CharacterToFileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase,
    private val dataStore: DataStoreRepository,
    private val characterToFileUseCase: CharacterToFileUseCase,
    private val characterCreationUseCases: CharacterCreationUseCases,
): ViewModel() {

    private val _characterList = MutableStateFlow(emptyList<Character>())
    val characterList = _characterList.asStateFlow()

    val disclaimerMode = dataStore.readDisclaimerMode
    val darkMode = dataStore.readDarkMode

    init{
        getCharacters()
    }

    private fun getCharacters(){
        getCharacterListUseCase().onEach { result ->
            _characterList.value = result
        }.launchIn(viewModelScope)
    }

    fun saveDisclaimerMode(mode: Boolean) = viewModelScope.launch(Dispatchers.IO){
        dataStore.setDisclaimerMode(mode)
    }

    fun saveDarkMode(mode: Boolean) = viewModelScope.launch(Dispatchers.IO){
        dataStore.setDarkMode(mode)
    }

    fun getCharacterFromFile(inputStream: InputStream): Character? {
        return characterToFileUseCase.readObjectFromFile(inputStream)
    }

    fun addSharedCharacter(character: Character) {
        characterCreationUseCases.addCharacterUseCase.invoke(character).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    CharacterState(success = true)
                }
                is Resource.Error -> {
                    CharacterState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    CharacterState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }
}
