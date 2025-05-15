package com.aracne.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesRepository(private val dataStore: DataStore<Preferences>) {
    private val USERID = longPreferencesKey("user_id")

    private val NAME = stringPreferencesKey("role")

    val idFlow: Flow<Long>
    = dataStore.data
    .map { preferences ->
        preferences[USERID] ?: 0
    }

    suspend fun saveId(id: Long) {
        dataStore.edit { preferences ->
            preferences[USERID] = id
        }
    }

    val nameFlow: Flow<String> = dataStore.data
    .map { preferences ->
        preferences[NAME] ?: ""
    }

    suspend fun saveName(name: String) {
        dataStore.edit { preferences ->
            preferences[NAME] = name
        }
    }
}