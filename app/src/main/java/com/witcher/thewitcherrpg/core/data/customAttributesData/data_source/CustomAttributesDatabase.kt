package com.witcher.thewitcherrpg.core.data.customAttributesData.data_source

import android.content.Context
import androidx.room.*
import com.witcher.thewitcherrpg.core.domain.model.*

@Database(
    entities = [CustomEquipment::class, CustomWeapon::class, CustomMagic::class],
    version = 1,
    exportSchema = true,
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2)
//    ]
)
@TypeConverters(MyTypeMagicConverters::class, MyTypeWeaponConverters::class, MyTypeEquipmentConverters::class)

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