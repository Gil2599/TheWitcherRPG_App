package com.example.thewitcherrpg.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Character>>
    private val repository: CharacterRepository

    init{
        val charDao = CharacterDatabase.getDatabase(application).characterDao()
        repository = CharacterRepository(charDao)
        readAllData = repository.readAllData
    }

    fun addChar(character: Character){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addChar(character)
        }
    }

    fun deleteChar(character: Character){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteChar(character)
        }
    }

    fun updateChar(character: Character){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateChar(character)
        }
    }

}
