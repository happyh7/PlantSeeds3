package com.bps.plantseeds3.common.config

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AppConfig.Preferences.PREF_NAME)

class PreferencesManager(private val context: Context) {
    private val dataStore = context.dataStore

    val themeMode: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[THEME_MODE_KEY] ?: "system"
        }

    val language: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[LANGUAGE_KEY] ?: "sv"
        }

    suspend fun setThemeMode(mode: String) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = mode
        }
    }

    suspend fun setLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }

    companion object {
        val THEME_MODE_KEY = stringPreferencesKey(AppConfig.Preferences.KEY_THEME_MODE)
        val LANGUAGE_KEY = stringPreferencesKey(AppConfig.Preferences.KEY_LANGUAGE)
    }
} 