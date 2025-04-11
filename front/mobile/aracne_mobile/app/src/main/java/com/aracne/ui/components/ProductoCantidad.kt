package com.aracne.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aracne.R

@Composable
fun ProductoCantidad(contenido: String){
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.size(40.dp).background(
            color = colorResource(R.color.light_gray),
            shape = RoundedCornerShape(12.dp)
        )
    )
    {
        Text(
            contenido,
            style = TextStyle(
                fontSize = 16.sp
            )
        )
    }
}