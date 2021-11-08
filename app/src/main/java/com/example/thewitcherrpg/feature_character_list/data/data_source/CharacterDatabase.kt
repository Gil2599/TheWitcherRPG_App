package com.example.thewitcherrpg.feature_character_list.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.thewitcherrpg.feature_character_list.domain.model.Character
import com.example.thewitcherrpg.feature_character_list.domain.model.MyTypeConverters

@Database(
    entities = [Character::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MyTypeConverters::class)

abstract class CharacterDatabase: RoomDatabase() {

    abstract fun getDao(): CharacterDao

    companion object {
        @Volatile
        private var INSTANCE: CharacterDatabase? = null

        fun getDatabase(context: Context): CharacterDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharacterDatabase::class.java,
                    "character_table"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}