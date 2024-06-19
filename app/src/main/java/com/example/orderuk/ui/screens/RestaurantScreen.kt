package com.example.orderuk.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.orderuk.R
import com.example.orderuk.data.CartItem
import com.example.orderuk.domain.CartEvents
import com.example.orderuk.domain.Dishes
import com.example.orderuk.domain.RestaurantState
import com.example.orderuk.domain.RestaurantViewModel
import com.example.orderuk.ui.components.LoadingScreen
import com.example.orderuk.ui.theme.DarkBlue
import com.example.orderuk.ui.theme.OrderukTheme

@Composable
fun RestaurantScreen(
    modifier: Modifier = Modifier,
    restaurantViewModel: RestaurantViewModel = viewModel(),
    cartUiEvent: (CartEvents) -> Unit
) {
    val screenState by restaurantViewModel.uiState.collectAsStateWithLifecycle()

    when (screenState) {
        is RestaurantState.Loading -> LoadingScreen()
        is RestaurantState.Success -> SuccessScreen(
            dishes = (screenState as RestaurantState.Success).dishes,
            cartUiEvent = cartUiEvent,
            modifier = modifier
        )

        is RestaurantState.Error -> {}
    }
}

@Composable
fun SuccessScreen(
    modifier: Modifier = Modifier,
    dishes: List<Dishes>,
    cartUiEvent: (CartEvents) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.restaurant_menu),
                        contentDescription = stringResource(
                            id = R.string.menu
                        ),
                        modifier = Modifier.size(60.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = stringResource(id = R.string.menu),
                    color = DarkBlue,
                    style = MaterialTheme.typography.displayLarge
                )
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    text = "Pizzas",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.width(18.dp))
                Box {

                    Image(
                        painter = painterResource(id = R.drawable.down_button),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            dishes.forEach { dish ->
                DishItem(dish = dish, cartUiEvent = cartUiEvent)
            }
        }
    }
}

@Composable
fun DishItem(modifier: Modifier = Modifier, dish: Dishes, cartUiEvent: (CartEvents) -> Unit) {
    Card(

        colors = CardDefaults.cardColors(
            containerColor = Color.White,

            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 10.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = dish.name,
                        modifier = Modifier.widthIn(max = 200.dp),
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        for (i in 1..dish.spiceLevel.toInt()) {
                            Image(
                                painter = painterResource(id = R.drawable.hot_cihili),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        if (dish.spiceLevel.toInt() != 5) {
                            for (i in (dish.spiceLevel.toInt() + 1)..5) {
                                Image(
                                    painter = painterResource(id = R.drawable.cold_chili),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                    }
                }
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(100))
                ) {
                    AsyncImage(
                        model = dish.image,
                        contentDescription = dish.name,
                        modifier = Modifier.size(180.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = dish.description,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(190.dp),
                modifier = Modifier.heightIn(max = 3000.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)

            ) {
                items(items = dish.sizes.toList()) { dishSize ->
                    OutlinedButton(
                        onClick = {
                            cartUiEvent(
                                CartEvents.AddToCart(
                                    cartItem = CartItem(
                                        productName = dish.name,
                                        price = dishSize.second.toInt(),
                                        quantity = 1,
                                        productId = dish.id,
                                        imageUrl = dish.image ?: ""
                                    )
                                )
                            )
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                        ),
                        border = BorderStroke(width = 1.dp, color = Color(0xFF03081F)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = dishSize.first.split("_").joinToString(" ")
                                .capitalize(locale = Locale("en-US")),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black,
                            softWrap = true,

                            )
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFF028643)
                                )
                                .padding(vertical = 2.dp, horizontal = 5.dp)
                        ) {
                            Text(text = "£${dishSize.second.toDouble()}", softWrap = false)
                        }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RestaurantScreenPreview(modifier: Modifier = Modifier) {
    OrderukTheme {
        RestaurantScreen(
            modifier = Modifier
                .fillMaxWidth()
                .safeContentPadding(),
            cartUiEvent = {}
        )
    }
}