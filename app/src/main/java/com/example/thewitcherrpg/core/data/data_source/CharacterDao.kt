package com.example.thewitcherrpg.core.data.data_source

import androidx.room.*
import com.example.thewitcherrpg.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addChar(character: Character)

    @Query("SELECT * FROM character_table ORDER BY id ASC")
    fun readAllData(): Flow<List<Character>>

    @Query("SELECT * FROM character_table WHERE id = :id")
    suspend fun getCharacterById(id: Int): Character?

    @Delete
    suspend fun deleteChar(character: Character)

    @Update
    suspend fun updateChar(character: Character)

}