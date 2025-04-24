package com.aracne.ui.components

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aracne.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BotonProductoCantidad(imagen: Int, onClick: () -> Unit){
    var isPressed by remember { mutableStateOf(false) }
    val backgroundColorCantidad = if (isPressed) R.color.gray else R.color.light_gray
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.size(40.dp)
            .background(color = colorResource(backgroundColorCantidad), shape = RoundedCornerShape(12.dp))
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isPressed = true
                        onClick()
                        true
                    }
                    MotionEvent.ACTION_UP,
                    MotionEvent.ACTION_CANCEL -> {
                        isPressed = false
                        true
                    }
                    else -> false
                }
            }
    ) {
        Image(
            painter = painterResource(imagen),
            contentDescription = "",
            modifier = Modifier.size(30.dp)
        )
    }
}