package com.aracne.ui

/**
 * Esto es una clase que representa los diferentes estados de la interfaz de manera estructurada y en tiempos de compilación.
 * La clase sealed asegura que todos los posibles estados estan definidos.
 * Subclases:
 * Loading: esto representa el estado donde hay un proceso cargando.
 * Success<T>: esto representa el estado exitoso, contiene el tipo T con los datos cargados.
 * Error: esto representa el estado de error, el cual contiene un mensaje de error el cual es un String.
 */
sealed class UIState {
    object Loading: UIState()
    data class Success<T>(val data: T): UIState()
    data class Error(val message: String): UIState()
}