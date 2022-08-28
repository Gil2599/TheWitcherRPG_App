package com.witcher.thewitcherrpg.core.data.customAttributesData.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.witcher.thewitcherrpg.core.domain.model.CustomEquipment
import com.witcher.thewitcherrpg.core.domain.model.CustomMagic
import com.witcher.thewitcherrpg.core.domain.model.MyTypeEquipmentConverters
import com.witcher.thewitcherrpg.core.domain.model.MyTypeMagicConverters

@Database(
    entities = [CustomEquipment::class, CustomMagic::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MyTypeMagicConverters::class, MyTypeEquipmentConverters::class)

abstract class CustomAttributesDatabase: RoomDatabase() {

    abstract fun getDao(): CustomAttributesDao

    companion object {
        @Volatile
        private var INSTANCE: CustomAttributesDatabase? = null

        fun getDatabase(context: Context): CustomAttributesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CustomAttributesDatabase::class.java,
                    "custom_attributes"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}