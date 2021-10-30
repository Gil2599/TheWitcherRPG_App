package com.example.thewitcherrpg.data

import androidx.lifecycle.LiveData
import com.example.thewitcherrpg.data.Character
import com.example.thewitcherrpg.data.CharacterDao

class CharacterRepository(private val charDao: CharacterDao) {

    val readAllData: LiveData<List<Character>> = charDao.readAllData()

    suspend fun addChar(character: Character){
        charDao.addChar(character)
    }

    suspend fun deleteChar(character: Character){
        charDao.deleteChar(character)
    }

    suspend fun updateChar(character: Character){
        charDao.updateChar(character)
    }

}