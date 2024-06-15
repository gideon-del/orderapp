package com.example.orderuk.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.orderuk.R
import com.example.orderuk.domain.RestaurantViewModel
import com.example.orderuk.ui.theme.DarkBlue
import com.example.orderuk.ui.theme.OrderukTheme

@Composable
fun RestaurantScreen(modifier: Modifier = Modifier, restaurantViewModel: RestaurantViewModel = viewModel()) {
    val dishes  by restaurantViewModel.uiState.collectAsStateWithLifecycle()
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {

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
        DishItem()
        Spacer(modifier = Modifier.height(10.dp))
        DishItem()
    }
}

@Composable
fun DishItem(modifier: Modifier = Modifier) {
    Card(

        colors = CardDefaults.cardColors(
            containerColor = Color.White,

            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
        modifier=modifier
        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 10.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Farm House Xtreme Pizza",
                        modifier = Modifier.widthIn(max = 200.dp),
                        style = MaterialTheme.typography.displayMedium,

                        )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        listOf(1, 2).forEach {
                            Image(
                                painter = painterResource(id = R.drawable.hot_cihili),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        listOf(1, 2, 3).forEach {
                            Image(
                                painter = painterResource(id = R.drawable.cold_chili),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.pizza_dish),
                        contentDescription = "Farm House Xtreme Pizza",
                        modifier = Modifier.size(180.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "1 McChicken™, 1 Big Mac™, 1 Royal Cheeseburger, 3 medium sized French Fries , 3 cold drinks",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(190.dp),
                modifier = Modifier.heightIn(max = 3000.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)

            ) {
                items(items = listOf("Small", "Medium", "Large","XL Large with Sauces")) {
                    OutlinedButton(
                        onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                        ),
                        border= BorderStroke(width= 1.dp, color=Color(0xFF03081F)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black,
                            softWrap =false
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFF028643)
                                )
                                .padding(vertical = 2.dp, horizontal = 5.dp)
                        ) {
                            Text(text = "£21.90", softWrap =false)
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
                .safeContentPadding()
        )
    }
}