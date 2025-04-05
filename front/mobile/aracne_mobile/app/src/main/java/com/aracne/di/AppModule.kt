package com.aracne.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.aracne.data.repository.ShopRepository
import com.aracne.data.repository.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Extensión de la clase Context para proporcionar acceso a DataStore.
 *
 * Se define una instancia de DataStore con el nombre "user_preferences".
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

/**
 * Módulo de Hilt encargado de proporcionar las dependencias principales de la aplicación.
 *
 * Este módulo se instala en el SingletonComponent, lo que significa que sus dependencias
 * estarán disponibles a lo largo de toda la aplicación.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * Proporciona una instancia singleton de ShopRepository.
     *
     * @return Instancia única de ShopRepository que maneja la lógica de obtención de datos del laboratorio.
     */
    @Provides
    @Singleton
    fun provideLaboratoryRepository(): ShopRepository {
        return ShopRepository()
    }

    /**
     * Proporciona una instancia singleton de PreferencesRepository.
     *
     * Este repositorio gestiona las preferencias del usuario utilizando DataStore.
     *
     * @param context Contexto de la aplicación, necesario para acceder a DataStore.
     * @return Instancia única de PreferencesRepository para la gestión de preferencias.
     */
    @Provides
    @Singleton
    fun providePreferencesRepository(@ApplicationContext context: Context): PreferencesRepository {
        return PreferencesRepository(context.dataStore)
    }
}