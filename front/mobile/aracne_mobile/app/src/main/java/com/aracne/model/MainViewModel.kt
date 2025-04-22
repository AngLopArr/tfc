package com.aracne.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aracne.data.model.Product
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

@HiltViewModel
class MainViewModel @Inject constructor(
    private val shopRepository: ShopRepository
) : ViewModel() {


    var productos by mutableStateOf(listOf<List<Product>>())

    fun getInitialProducts(){
        viewModelScope.launch {
            try {
                var respuesta = shopRepository.get4Products(1)
                if (respuesta != null) {
                    productos = respuesta.chunked(2)
                    respuesta = shopRepository.get4Products(2)
                    if (respuesta != null) {
                        productos += respuesta.chunked(2)
                        /*respuesta = shopRepository.get4Products(3)
                        if (respuesta != null) {
                            productos += respuesta.chunked(2)
                        } else {
                            println("Error.")
                        }*/
                    } else {
                        println("Error.")
                    }
                } else {
                    println("Error.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun addProducts(group: Int) {
        viewModelScope.launch {
            try {
                val respuesta = shopRepository.get4Products(group)
                if (respuesta != null) {
                    productos += respuesta.chunked(2)
                } else {
                    println("Error.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}


