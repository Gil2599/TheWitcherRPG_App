package com.example.thewitcherrpg.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

object Constants {

    enum class Professions {
        BARD,
        CRIMINAL,
        CRAFTSMAN,
        DOCTOR,
        MAGE,
        MAN_AT_ARMS,
        PRIEST,
        WITCHER,
        MERCHANT,
        NOBLE,
        PEASANT
    }

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


}