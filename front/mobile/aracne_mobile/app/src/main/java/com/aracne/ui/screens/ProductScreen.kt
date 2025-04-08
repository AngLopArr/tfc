package com.aracne.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest

@Composable
fun ProductScreen(productoId: Int){
    Column(
        modifier = Modifier.fillMaxWidth().padding(PaddingValues(15.dp))
    ){
        Box(
            modifier = Modifier
                .height(400.dp) // altura deseada
                .fillMaxWidth()
                .clipToBounds() // importante para que se recorte si se sale del contenedor
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data("https://www.marie-claire.es/wp-content/uploads/sites/2/2023/04/04/642bf68e97173.jpeg")
                    .build(),
                contentDescription = "",
                modifier = Modifier.clip(RoundedCornerShape(12.dp)).fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    }
}