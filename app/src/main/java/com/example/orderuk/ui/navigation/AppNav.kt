package com.example.orderuk.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.orderuk.R
import com.example.orderuk.data.CartItem
import com.example.orderuk.domain.CartEvents
import com.example.orderuk.domain.CartViewModel
import com.example.orderuk.ui.screens.HomeScreen
import com.example.orderuk.ui.screens.OrderScreen
import com.example.orderuk.ui.screens.RestaurantScreen
import com.example.orderuk.ui.theme.DarkBlue
import com.example.orderuk.ui.theme.OrderukTheme

import kotlinx.serialization.Serializable


sealed class Routes {
    @Serializable
    data object Home : Routes()

    @Serializable
    data object Restaurant : Routes()
    @Serializable
    data object Order: Routes()
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
                composable<Routes.Order> {
                    OrderScreen(navigateToHome = {
                       val isPopped= navController.popBackStack<Routes.Home>(inclusive = true)
                        if(!isPopped){
                            navController.navigate(route=Routes.Home)
                        }
                    })
                }
            }
        }
        if (showCart) {
            CartDetail(cartItems = cartState, closeCart = {
                showCart = false
            }, cartUiEvent = cartViewModel::onUIEvent, navigateToOrder = {
                navController.navigate(route = Routes.Order)
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
    cartUiEvent: (CartEvents) ->  Unit,
    navigateToOrder : () -> Unit
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
                .fillMaxSize(0.9f)
                .background(Color.White)
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.Center)
                .padding(horizontal = 10.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (cartItems.isEmpty()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = Color.Black
                    )
                    Text(text = stringResource(id = R.string.empty_cart), color = Color.Black)
                }
            } else {
                val total: Int = cartItems.fold(0) { acc, cartItem ->
                    acc + (cartItem.price * cartItem.quantity)
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    cartItems.forEach { cartItem ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(100))
                                ) {
                                    AsyncImage(
                                        model = cartItem.imageUrl,
                                        contentDescription = cartItem.productName,
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Text(
                                    text = cartItem.productName,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = DarkBlue
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                FilledIconButton(
                                    onClick = {
                                        cartUiEvent(CartEvents.UpdateCartItem(
                                            cartItem.copy(
                                                quantity = if( cartItem.quantity <= 1) 1 else cartItem.quantity-1
                                            )
                                        ))
                                    },
                                    modifier = Modifier.wrapContentSize(),
                                    colors = IconButtonDefaults.filledIconButtonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = DarkBlue
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.minus),
                                        contentDescription = "Decrease"
                                    )
                                }
                                Box(
                                    modifier = Modifier

                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = cartItem.quantity.toString(),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = DarkBlue
                                    )
                                }
                                FilledIconButton(
                                    onClick = {cartUiEvent(CartEvents.UpdateCartItem(
                                        cartItem.copy(
                                            quantity = cartItem.quantity+1
                                        )
                                    )) },
                                    modifier = Modifier.wrapContentSize(),
                                    colors = IconButtonDefaults.filledIconButtonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = DarkBlue
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.add),
                                        contentDescription = "Increase",

                                        )
                                }
                            }

                            IconButton(onClick = {
                                cartUiEvent(CartEvents.DeleteCartItem(cartItem))
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete",
                                    tint = DarkBlue
                                )
                            }


                        }
                    }
                }

                Button(
                    onClick = {
                        navigateToOrder()
                        closeCart()
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White
                    ), shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "£$total",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = stringResource(id = R.string.place_order))
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

            ),
            closeCart = {},
            cartUiEvent = {},
            navigateToOrder = {}
        )
    }
}