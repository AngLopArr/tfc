package com.aracne

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Esta es la clase base de la aplicación que se extiende de "Application".
 * Esto incorpora "@HiltAndroidApp" para añadir las inyecciones de dependencias.
 */
@HiltAndroidApp
class App: Application(){}