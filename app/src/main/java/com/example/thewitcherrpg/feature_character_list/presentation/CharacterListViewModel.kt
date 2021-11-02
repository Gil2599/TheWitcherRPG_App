package com.example.thewitcherrpg.feature_character_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thewitcherrpg.core.data.repository.CharacterRepositoryImpl
import com.example.thewitcherrpg.core.domain.model.Character
import com.example.thewitcherrpg.feature_character_list.domain.use_cases.CharacterListUseCases
import com.example.thewitcherrpg.feature_character_list.domain.use_cases.GetCharacterListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val repositoryImpl: CharacterRepositoryImpl,
    private val getCharacterListUseCase: GetCharacterListUseCase
): ViewModel() {

    private val _characterList = MutableStateFlow(emptyList<Character>())
    val characterList = _characterList.asStateFlow()

    init{
        getCharacters()
    }

    private fun getCharacters(){
        getCharacterListUseCase().onEach { result ->
            _characterList.value = result
        }.launchIn(viewModelScope)
    }
}
