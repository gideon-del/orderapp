package com.example.orderuk.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.orderuk.R
import com.example.orderuk.ui.theme.DarkBlue
import com.example.orderuk.ui.theme.Orange
import com.example.orderuk.ui.theme.OrderukTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {


    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(DarkBlue)
                .padding(horizontal = 20.dp, vertical = 15.dp),
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
            Text(
                text = stringResource(id = R.string.postcode),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .background(Color.White),
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
                    }

                )
                Box(
                    modifier= Modifier.fillMaxHeight().clip(RoundedCornerShape(100.dp
                    )).background(Orange)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.next_page),
                        contentDescription = null,
                        modifier = Modifier.widthIn(min=34.dp).heightIn(min=34.dp)
                    )
                }
            }

        }

    }
}

@Preview(showBackground = true, widthDp = 400)
@Composable
fun HomeScreenPreview(modifier: Modifier = Modifier) {
    OrderukTheme {
        HomeScreen(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxSize()
        )
    }
}