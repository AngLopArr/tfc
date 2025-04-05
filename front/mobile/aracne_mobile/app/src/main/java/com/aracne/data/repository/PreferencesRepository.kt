package com.aracne.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repositorio encargado de gestionar las preferencias del usuario utilizando DataStore.
 *
 * Permite almacenar y recuperar el token de autenticación, el ID del usuario y su rol dentro de la aplicación.
 *
 * @property dataStore Instancia de DataStore utilizada para la gestión de preferencias del usuario.
 */
class PreferencesRepository(private val dataStore: DataStore<Preferences>) {
    /** Clave para almacenar el token de autenticación en DataStore. */
    private val TOKEN_KEY = stringPreferencesKey("token")

    /** Clave para almacenar el ID del usuario en DataStore. */
    private val USER_ID_KEY = intPreferencesKey("user_id")

    /** Clave para almacenar el rol del usuario en DataStore. */
    private val ROLE_KEY = stringPreferencesKey("role")

    /**
     * Flujo que obtiene el token almacenado del usuario.
     *
     * @return Un `Flow<String>` que emite el token de autenticación del usuario.
     * Si no hay ningún token almacenado, retorna una cadena vacía.
     */
    val tokenFlow: Flow<String> = dataStore.data
    .map { preferences ->
        preferences[TOKEN_KEY] ?: ""
    }

    /**
     * Guarda un nuevo token en DataStore.
     *
     * @param token Token de autenticación a almacenar.
     */
    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    /**
     * Flujo que obtiene el ID del usuario almacenado.
     *
     * @return Un `Flow<Int>` que emite el ID del usuario. Si no hay ningún ID almacenado, retorna 0.
     */
    val idFlow: Flow<Int> = dataStore.data
    .map { preferences ->
        preferences[USER_ID_KEY] ?: 0
    }

    /**
     * Guarda un nuevo ID de usuario en DataStore.
     *
     * @param id Identificador único del usuario a almacenar.
     */
    suspend fun saveId(id: Int) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = id
        }
    }

    /**
     * Flujo que obtiene el rol del usuario almacenado.
     *
     * @return Un `Flow<String>` que emite el rol del usuario. Si no hay ningún rol almacenado, retorna una cadena vacía.
     */
    val roleFlow: Flow<String> = dataStore.data
    .map { preferences ->
        preferences[ROLE_KEY] ?: ""
    }

    /**
     * Guarda un nuevo rol de usuario en DataStore.
     *
     * @param role Rol del usuario a almacenar (por ejemplo, "ADMIN" o "USER").
     */
    suspend fun saveRole(role: String) {
        dataStore.edit { preferences ->
            preferences[ROLE_KEY] = role
        }
    }
}