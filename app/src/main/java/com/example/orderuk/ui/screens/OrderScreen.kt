package com.example.orderuk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.orderuk.domain.CartEvents
import com.example.orderuk.domain.CartViewModel
import com.example.orderuk.ui.theme.OrderukTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun OrderScreen(
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel = viewModel(factory = CartViewModel.Factory),
    navigateToHome:() -> Unit
) {
val carts by cartViewModel.uiState.collectAsStateWithLifecycle(listOf())
    var shipment by remember {
        mutableStateOf("delivery")
    }
    var coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row {
            Button(
                onClick = {
                    shipment = "delivery"
                },
                modifier = Modifier
                    .weight(1f)
                    .background(Color(if (shipment.contains("delivery")) 0xFFEEEEEE else 0x55EEEEEE))
                    .padding(horizontal = 10.dp, vertical = 16.dp)

            ) {
                Text(
                    text = "Delivery",
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

            }
            Button(
                onClick = {
                    shipment = "collection"
                },
                modifier = Modifier
                    .weight(1f)
                    .background(Color(if (shipment.contains("delivery")) 0xFFEEEEEE else 0x55EEEEEE))
                    .padding(horizontal = 10.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Collection",
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.Black,
                    textAlign = TextAlign.Center,

                )

            }
        }

       Box{
           FilledTonalButton(onClick = {
               coroutineScope.launch {
                   cartViewModel.onUIEvent(CartEvents.PlaceOrder(carts,shipment, navigateToHome))

               }

           }) {
               Text(text = "Place Order")
           }
       }

    }
}

@Preview
@Composable
fun OrderScreenPreview(modifier: Modifier = Modifier) {
    OrderukTheme {
        OrderScreen(navigateToHome = {})
    }
}