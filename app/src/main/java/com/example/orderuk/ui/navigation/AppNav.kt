package com.example.orderuk.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.orderuk.ui.screens.HomeScreen
import com.example.orderuk.ui.screens.RestaurantScreen
import kotlinx.serialization.Serializable

sealed class Routes{
    @Serializable
    data object Home: Routes()

    @Serializable
    data object Restaurant: Routes()
}
@Composable
fun AppNav(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = Routes.Home, modifier = modifier){
        composable<Routes.Home> {
            HomeScreen( navigateToRestaurant = {
                navController.navigate(route=Routes.Restaurant)
            })
        }
        composable<Routes.Restaurant> {
            RestaurantScreen()
        }
    }
}