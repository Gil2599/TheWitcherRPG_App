package com.witcher.thewitcherrpg.core.dataStoreRepository

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
        val darkMode = booleanPreferencesKey("darkMode")
        val disclaimerMode = booleanPreferencesKey("disclaimer")
        val characterInformation = booleanPreferencesKey("charInfo")
        val statsInfo = booleanPreferencesKey("statsInfo")
        val skillsInfo = booleanPreferencesKey("skillsInfo")
        val skillTreeInfo = booleanPreferencesKey("skillTreeInfo")
    }

    private val settingsDataStore = appContext.dataStore

    suspend fun setDisclaimerMode(enabled: Boolean) {
        settingsDataStore.edit { settings ->
            settings[PreferenceKeys.disclaimerMode] = enabled
        }
    }

    suspend fun setCharacterInfoMode(enabled: Boolean){
        settingsDataStore.edit { settings ->
            settings[PreferenceKeys.characterInformation] = enabled
        }
    }

    suspend fun setStatsInfoMode(enabled: Boolean){
        settingsDataStore.edit { settings ->
            settings[PreferenceKeys.statsInfo] = enabled
        }
    }

    suspend fun setSkillsInfoMode(enabled: Boolean){
        settingsDataStore.edit { settings ->
            settings[PreferenceKeys.skillsInfo] = enabled
        }
    }

    suspend fun setSkillTreeInfoMode(enabled: Boolean){
        settingsDataStore.edit { settings ->
            settings[PreferenceKeys.skillTreeInfo] = enabled
        }
    }

    suspend fun setDarkMode(enabled: Boolean){
        settingsDataStore.edit { settings ->
            settings[PreferenceKeys.darkMode] = enabled
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

    val readCharInfoMode: Flow<Boolean> = settingsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preference ->
            val charInfoMode = preference[PreferenceKeys.characterInformation] ?: true
            charInfoMode
        }

    val readStatsInfoMode: Flow<Boolean> = settingsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preference ->
            val statsInfoMode = preference[PreferenceKeys.statsInfo] ?: true
            statsInfoMode
        }

    val readSkillsInfoMode: Flow<Boolean> = settingsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preference ->
            val skillsInfoMode = preference[PreferenceKeys.skillsInfo] ?: true
            skillsInfoMode
        }

    val readSkillTreeInfoMode: Flow<Boolean> = settingsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preference ->
            val skillTreeInfo = preference[PreferenceKeys.skillTreeInfo] ?: true
            skillTreeInfo
        }

    val readDarkMode: Flow<Boolean> = settingsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preference ->
            val darkMode = preference[PreferenceKeys.darkMode] ?: true
            darkMode
        }
}