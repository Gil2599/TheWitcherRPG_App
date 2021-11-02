package com.example.thewitcherrpg.feature_character_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thewitcherrpg.core.data.repository.CharacterRepositoryImpl
import com.example.thewitcherrpg.core.domain.model.Character
import com.example.thewitcherrpg.feature_character_list.domain.use_cases.CharacterListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val repositoryImpl: CharacterRepositoryImpl,
    characterListUseCases: CharacterListUseCases
): ViewModel() {

    val readAllData: Flow<List<Character>> = flow {
        repositoryImpl.getCharacters()
    }

    var characterList: Flow<List<Character>> = characterListUseCases.getCharacterListUseCase()
    //private val repositoryImpl: CharacterRepositoryImpl

    init{
        //val charDao = CharacterDatabase.getDatabase(application).characterDao()
        //repositoryImpl = CharacterRepositoryImpl(charDao)
    }

    fun addChar(character: Character){
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.addChar(character)
        }
    }

    fun deleteChar(character: Character){
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.deleteChar(character)
        }
    }

    fun updateChar(character: Character){
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.updateChar(character)
        }
    }

}
