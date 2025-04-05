package com.aracne.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aracne.R
import com.aracne.model.MainViewModel
import com.aracne.ui.navigation.Destinations

@Composable
fun CustomBottomNavBar(onClickNavMain: () -> Unit, onClickNavSecond: () -> Unit, onClickNavThird: () -> Unit, currentScreen: String){
    NavigationBar(containerColor = colorResource(id = R.color.purple_002)) {
        NavigationBarItem(
            selected = currentScreen == Destinations.PRODUCTOS,
            onClick = { onClickNavMain() },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.products),
                    contentDescription = stringResource(id = R.string.products),
                    modifier = Modifier.size(28.dp)
                )
            },
            label = { Text(text = stringResource(id = R.string.products), style = TextStyle(
                fontSize = 12.sp
            )) },
            colors = coloresNavigationBar()
        )
        NavigationBarItem(
            selected = currentScreen == Destinations.CART,
            onClick = { onClickNavSecond() },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.cart),
                    contentDescription = stringResource(id = R.string.cart),
                    modifier = Modifier.size(27.dp)
                )
            },
            label = { Text(stringResource(id = R.string.cart), style = TextStyle(
                fontSize = 12.sp
            )) },
            colors = coloresNavigationBar()
        )
        NavigationBarItem(
            selected = currentScreen == Destinations.PROFILE,
            onClick = { onClickNavThird() },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = stringResource(id = R.string.profile),
                    modifier = Modifier.size(23.dp)
                )
            },
            label = { Text(text = stringResource(id = R.string.profile), style = TextStyle(
                fontSize = 12.sp
            )) },
            colors = coloresNavigationBar()
        )
    }
}

@Composable
fun coloresNavigationBar(): NavigationBarItemColors {
    return NavigationBarItemColors(
        selectedIconColor = Color.Black,
        selectedTextColor = Color.Black,
        selectedIndicatorColor = colorResource(id = R.color.purple_001),
        unselectedIconColor = Color.Black,
        unselectedTextColor = Color.Black,
        disabledIconColor = Color.Black,
        disabledTextColor = Color.Black
    )
}