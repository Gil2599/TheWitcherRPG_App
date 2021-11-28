package com.example.thewitcherrpg.core.dataStoreRepository

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


const val PREFERENCE_NAME = "settings"
private val Context.dataStore by preferencesDataStore(PREFERENCE_NAME)

@Singleton
class DataStoreRepository @Inject constructor(@ApplicationContext appContext: Context) {

    private object PreferenceKeys {
        val disclaimerMode = booleanPreferencesKey("disclaimer")
    }

    private val settingsDataStore = appContext.dataStore

    suspend fun setDisclaimerMode(enabled: Boolean) {
        settingsDataStore.edit { settings ->
            settings[PreferenceKeys.disclaimerMode] = enabled
        }
    }

    val readDisclaimerMode: Flow<Boolean> = settingsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preference ->
            val disclaimerMode = preference[PreferenceKeys.disclaimerMode] ?: true
            disclaimerMode
        }
}