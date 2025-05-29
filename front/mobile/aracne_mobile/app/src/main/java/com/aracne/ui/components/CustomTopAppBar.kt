package com.aracne.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aracne.R
import com.aracne.ui.navigation.Destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(navController: NavController, currentScreen: String) {
    /*mainViewModel.getNotificaciones()*/
    CenterAlignedTopAppBar(
        title = {
            Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier.size(60.dp).padding(PaddingValues(0.dp, 6.dp, 0.dp, 6.dp)))
        },
        navigationIcon = {
            if(currentScreen != Destinations.PRODUCTOS && currentScreen != Destinations.CART && currentScreen != Destinations.PROFILE){
                Box(modifier = Modifier.padding(PaddingValues(7.dp, 0.dp, 0.dp, 0.dp)).clickable {
                        navController.popBackStack()
                    }
                ){
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Fecha para volver",
                        tint = Color.White,
                        modifier = Modifier.size(33.dp).background(colorResource(id = R.color.purple_001), shape = CircleShape).padding(6.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(R.color.purple_002))
    )
}
