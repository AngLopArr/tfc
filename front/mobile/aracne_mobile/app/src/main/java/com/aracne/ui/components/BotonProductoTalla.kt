package com.aracne.ui.components

import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aracne.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BotonProductoTalla(contenido: String, isPressed: Boolean, selectTalla: () -> Unit){
    val backgroundColorTalla = if (isPressed) R.color.purple_001 else R.color.purple_002
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.size(40.dp).background(
            color = colorResource(backgroundColorTalla),
            shape = RoundedCornerShape(12.dp)
        )
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        selectTalla()
                        true
                    }
                    else -> false
                }
            }
    )
    {
        Text(
            contenido,
            style = TextStyle(
                fontSize = 15.sp,
                color = colorResource(R.color.purple_000)
            )
        )
    }
}