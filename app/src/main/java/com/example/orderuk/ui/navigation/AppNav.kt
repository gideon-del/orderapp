package com.example.orderuk.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.orderuk.R
import com.example.orderuk.ui.screens.HomeScreen
import com.example.orderuk.ui.screens.RestaurantScreen
import com.example.orderuk.ui.theme.OrderukTheme
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object Home : Routes()

    @Serializable
    data object Restaurant : Routes()
}

@Composable
fun AppNav(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            TopBar()
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Routes.Home,
            modifier = modifier.padding(innerPadding)
        ) {
            composable<Routes.Home> {
                HomeScreen(navigateToRestaurant = {
                    navController.navigate(route = Routes.Restaurant)
                })
            }
            composable<Routes.Restaurant> {
                RestaurantScreen()
            }
        }
    }

}
@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White)
                .safeContentPadding()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(
                        id = R.string.app_name
                    )
                )
            }
            OutlinedIconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = Color(0xFF03081F),
                    modifier=Modifier.size(65.dp)
                )
            }
        }
    }

}

@Composable
fun CartDetail(modifier: Modifier = Modifier) {
Box {
    Box(modifier=Modifier.fillMaxSize())
}
}


@Preview
@Composable
fun AppNavPreview(modifier: Modifier = Modifier) {
    OrderukTheme {
        AppNav()
    }
}