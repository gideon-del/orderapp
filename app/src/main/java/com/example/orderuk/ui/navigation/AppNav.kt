package com.example.orderuk.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.orderuk.R
import com.example.orderuk.data.CartItem
import com.example.orderuk.domain.CartEvents
import com.example.orderuk.domain.CartViewModel
import com.example.orderuk.ui.screens.HomeScreen
import com.example.orderuk.ui.screens.RestaurantScreen
import com.example.orderuk.ui.theme.DarkBlue
import com.example.orderuk.ui.theme.Orange
import com.example.orderuk.ui.theme.OrderukTheme
import com.example.orderuk.ui.theme.PoppinsFont
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
    navController: NavHostController = rememberNavController(),
    cartViewModel: CartViewModel = viewModel(factory = CartViewModel.Factory)
) {
    val cartState by cartViewModel.uiState.collectAsStateWithLifecycle(initialValue = listOf())
    var showCart by remember {
        mutableStateOf(false)
    }
    val toggleCart = {
        showCart = !showCart
    }
    Box {


        Scaffold(
            topBar = {
                TopBar(openCart = toggleCart)
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
                    RestaurantScreen(cartUiEvent = cartViewModel::onUIEvent)
                }
            }
        }
        if (showCart) {
            CartDetail(cartItems = cartState, closeCart = {
                showCart = false
            }, deleteCart = { cartItem ->
                cartViewModel.onUIEvent(CartEvents.DeleteCartItem(cartItem))
            })
        }

    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier, openCart: () -> Unit) {
    Column(modifier = modifier) {
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
                    modifier = Modifier.size(65.dp)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            FilledTonalButton(
                modifier = Modifier
                    .weight(0.5f),
                onClick = openCart,
                shape = RoundedCornerShape(0)

            ) {
                Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "cart")
            }
        }
    }

}

@Composable
fun CartDetail(
    modifier: Modifier = Modifier,
    cartItems: List<CartItem>,
    closeCart: () -> Unit,
    deleteCart: (CartItem) -> Unit
) {

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x77000000))
                .clickable {
                    closeCart()
                }
        )
        Column(
            modifier = Modifier
                .fillMaxSize(0.8f)
                .background(Color.White)
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.Center)
                .padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            cartItems.forEach { cartItem ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .size(30.dp)
                            .background(Orange),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cartItem.quantity.toString(),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontFamily = PoppinsFont,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column {
                        Text(
                            text = "£${cartItem.price}",
                            fontFamily = PoppinsFont,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF028643),
                            fontSize = 20.sp,
                        )
                        Text(
                            text = cartItem.productName,
                            fontFamily = PoppinsFont,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkBlue,
                            fontSize = 20.sp,
                        )
                    }
                    IconButton(onClick = {
                        deleteCart(cartItem)
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
                    }


                }
            }
        }
    }
}


@Preview
@Composable
fun AppNavPreview(modifier: Modifier = Modifier) {
    OrderukTheme {
        CartDetail(
            cartItems = listOf(
                CartItem(
                    quantity = 1,
                    price = 21,
                    productName = "Pepperoni Pizza"
                )
            ),
            closeCart = {},
            deleteCart = {}
        )
    }
}