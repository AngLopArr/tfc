package com.aracne.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.aracne.model.MainViewModel

@Composable
fun ShoppingCartScreen(mainViewModel: MainViewModel){
    LazyColumn {
        items(mainViewModel.carrito) {
            item -> Text(item.producto?.name ?: "")
        }
    }
}