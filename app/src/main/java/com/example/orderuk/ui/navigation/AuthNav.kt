package com.example.orderuk.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.orderuk.ui.screens.LoginScreen
import com.example.orderuk.ui.screens.RegisterScreen
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable
object RegisterRoute

@Composable
fun AuthNav(modifier: Modifier = Modifier, navController: NavHostController= rememberNavController()) {
NavHost(navController = navController, startDestination = LoginRoute, modifier = modifier){
        composable<LoginRoute>{
                LoginScreen(
                        navigsteToRegister = {
                              navController.popBackStack(RegisterRoute, inclusive = true)
                        }
                )
        }
        composable<RegisterRoute> {
                RegisterScreen(
                        navigateToLogin = {
                                navController.popBackStack(LoginRoute, inclusive = true)
                        }
                )
        }
}
}