package com.aracne.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aracne.data.repository.ShopRepository
import com.aracne.data.repository.PreferencesRepository
import com.aracne.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * Instancia del constructor ViewModel que almacena los datos que la interfaz de usuario necesita mostrar y se encarga de preparar y transformar
 * datos antes de exponerlos a la vista.
 *
 * @property notificaciones: List mutable que contiene instancias ficticias de la data class Notification
 * @property categorias: List mutable que contiene instancias ficticias de la data class Categoria
 * @property materiales: List mutable que contiene instancias ficticias de la data class Material
 * @property prestamos: List mutable que contiene instancias ficticias de la data class Prestamo
 * @property incidencias: List mutable que contiene instancias ficticias de la data class Incidencia
 * @property token: Variable innmutable iniciada como cadena vacia.
 * @property user_id: Variable innmutable de id del usuario inciada en -1.
 * @property role: Variable innmutable de rol del cosnumidor de la app inciada como cadena vacia.
 *
 * Todas las propiedades mutables, cuando se modifican, provocan la recomposición de la aplicación, haciendo efectivos los cambios que se han
 * realizado sobre ellas y mostrándolos en pantalla.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val shopRepository: ShopRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() { }


