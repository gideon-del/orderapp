package com.example.orderuk.ui.screens


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.orderuk.R
import com.example.orderuk.ui.theme.DarkBlue
import com.example.orderuk.ui.theme.Orange
import com.example.orderuk.ui.theme.OrderukTheme
import com.example.orderuk.ui.theme.PoppinsFont

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navigateToRestaurant:() -> Unit) {
    val outerScrollState = rememberScrollState()
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Pass scroll events from inner to outer
                return if (available.y > 0) {
                    Offset.Zero
                } else {
                    Offset(0f, outerScrollState.dispatchRawDelta(available.y))
                }
            }
        }
    }


    Column(
        modifier = modifier
            .verticalScroll(outerScrollState)
            .padding(horizontal = 10.dp, vertical = 16.dp),

        ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(DarkBlue)
                .padding(horizontal = 20.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Text(
                text = stringResource(id = R.string.order_food),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.senses),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = stringResource(id = R.string.fast),
                    color = Orange,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayLarge,

                    )
            }
            Spacer(
                modifier = Modifier.height(
                    20.dp
                )
            )
            Text(
                text = stringResource(id = R.string.postcode),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(
                modifier = Modifier.height(
                    10.dp
                )
            )
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = "",
                    onValueChange = {},
                    shape = RoundedCornerShape(100),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        unfocusedPlaceholderColor = Color(0xFF000000),
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.ec34),
                            style = MaterialTheme.typography.bodySmall,
                        )
                    },
                    modifier = Modifier.height(45.dp),
                    textStyle = MaterialTheme.typography.bodySmall
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(
                            RoundedCornerShape(
                                100.dp
                            )
                        )
                        .background(Orange)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.next_page),
                        contentDescription = null,
                        modifier = Modifier
                            .widthIn(min = 34.dp)
                            .heightIn(min = 34.dp)
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.discount),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
            Row(

                modifier = Modifier
                    .widthIn(max = 180.dp)
                    .border(width = 2.dp, color = DarkBlue, shape = RoundedCornerShape(1000.dp))
                    .padding(vertical = 5.dp, horizontal = 10.dp),

                verticalAlignment = Alignment.CenterVertically,

                ) {
                Image(
                    painter = painterResource(id = R.drawable.drop_down),
                    contentDescription = null,
                    modifier = Modifier
                        .widthIn(min = 30.dp)
                        .height(30.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(id = R.string.pizza),
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = PoppinsFont,
                        fontWeight = FontWeight.Normal,

                        ),
                    color = Color.Black
                )
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            RestaurantItem()
            RestaurantItem()
            RestaurantItem()
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .background(Color(0x88D9D9D9))
                .padding(vertical = 10.dp, horizontal = 5.dp)
        ) {
            Text(
                text = stringResource(
                    id = R.string.order_uk
                ), style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                modifier = Modifier
                    .nestedScroll(nestedScrollConnection)
                    .heightIn(max = 800.dp),
                userScrollEnabled = false,
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(items = listOf(1, 2, 3, 4)) {
                    DishCategory()
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Popular Restaurants", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Restaurant(navigateToRestaurant=navigateToRestaurant)
            Restaurant(navigateToRestaurant=navigateToRestaurant)
            Restaurant(navigateToRestaurant=navigateToRestaurant)
        }
    }


}

@Composable
fun RestaurantItem(modifier: Modifier = Modifier) {
    val brush = Brush.linearGradient(
        0.0f to Color(0x11FFFFFF),
        0.19f to Color(0x33FFFFFF),
        0.89f to Color(0x7703081F),
        start = Offset(150f, 10f),
        end = Offset(0f, 150f)
    )
    Column(modifier) {
        Box(modifier = Modifier.clip(RoundedCornerShape(15.dp))) {

            Image(
                painter = painterResource(id = R.drawable.restaurant),
                contentDescription = null,
                modifier = Modifier.size(150.dp),

                )
            Canvas(modifier = Modifier.size(150.dp), onDraw = {
                drawRect(brush)
            })
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = DarkBlue,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .width(60.dp)
                    .height(46.dp)
                    .align(Alignment.TopEnd)
                    .padding(end = 10.dp),
                shape = RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "-17%",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }

            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Restaurant", style = MaterialTheme.typography.bodySmall, color = Orange
        )
        Text(
            text = "Butterbrot Caf’e London",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.widthIn(max = 130.dp)
        )
    }
}

@Composable
fun DishCategory(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.dish_item),
                contentDescription = "Burgers & Fast food",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop

            )

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkBlue)
                .padding(horizontal = 10.dp, vertical = 5.dp)

        ) {
            Text(
                text = stringResource(id = R.string.burger),
                style = MaterialTheme.typography.labelLarge,
                color = Orange
            )
            Text(
                text = stringResource(id = R.string.burger),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}

@Composable
fun Restaurant(modifier: Modifier = Modifier, navigateToRestaurant:() -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth().clickable {
            navigateToRestaurant()
        },

    ) {
       
            Image(
                painter = painterResource(id = R.drawable.mcdonald),
                contentDescription = null,
                contentScale = ContentScale.Crop,

            )

        Column(
            modifier = Modifier
                .background(Orange)
                .fillMaxWidth()
                .padding(7.dp)

        ) {
            Text(
                text = "McDonald’s London",
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 2000)
@Composable
fun HomeScreenPreview(modifier: Modifier = Modifier) {
    OrderukTheme {
        HomeScreen(
            modifier = Modifier
                .fillMaxSize(),
            navigateToRestaurant = {}
        )
    }
}