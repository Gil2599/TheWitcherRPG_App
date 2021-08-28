package com.example.thewitcherrpg.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.thewitcherrpg.data.Character

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addChar(character: Character)

    @Query("SELECT * FROM character_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Character>>

    @Delete
    suspend fun deleteChar(character: Character)

    @Update
    suspend fun updateChar(character: Character)

}