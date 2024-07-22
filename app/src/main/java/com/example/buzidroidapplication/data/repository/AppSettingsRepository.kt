package com.example.buzidroidapplication.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class AppSettingsRepository(
    private val dataStore: DataStore<Preferences>
) {
    val appSettings: Flow<AppSettings> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { preferences ->
            val predictionModeEnabled = preferences[PreferencesKeys.PREDICTION_MODE_ENABLED] ?: true
            val userName = preferences[PreferencesKeys.USER_NAME] ?: ""

            AppSettings(userName, predictionModeEnabled)
        }

    suspend fun updateUserName(userName: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = userName
        }
    }

    suspend fun updateAppMode(predictionModeEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.PREDICTION_MODE_ENABLED] = predictionModeEnabled
        }
    }

    data class AppSettings(
        val userName: String,
        val predictionModeEnabled: Boolean
    )

    private object PreferencesKeys {
        val USER_NAME = stringPreferencesKey("user_name")
        val PREDICTION_MODE_ENABLED = booleanPreferencesKey("prediction_mode_enabled")
    }
}