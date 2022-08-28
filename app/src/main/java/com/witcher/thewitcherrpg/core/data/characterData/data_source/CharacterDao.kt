package com.witcher.thewitcherrpg.core.data.characterData.data_source

import androidx.room.*
import com.witcher.thewitcherrpg.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addChar(character: Character)

    @Query("SELECT * FROM character_table ORDER BY id ASC")
    fun readAllData(): Flow<List<Character>>

    @Query("SELECT * FROM character_table WHERE id = :id")
    fun getCharacterById(id: Int): Flow<Character>

    @Delete
    suspend fun deleteChar(character: Character)

    @Update
    suspend fun updateChar(character: Character)

}