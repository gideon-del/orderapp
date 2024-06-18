package com.example.orderuk.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.orderuk.data.CartContainer
import com.example.orderuk.data.CartItem
import com.example.orderuk.orderUkApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class CartViewModel(private val cartRepo: CartContainer): ViewModel() {
     val uiState:Flow<List<CartItem>> = cartRepo.getCartItems()

fun onUIEvent(event: CartEvents){
    when(event) {
        is CartEvents.AddToCart -> addCartItem(productName = event.productName, price = event.price)
        is CartEvents.DeleteCartItem -> deleteCartItem(event.cartItem)
    }
}
  private fun addCartItem(productName: String, price: Int){
       viewModelScope.launch {

           cartRepo.insertCartItem(CartItem(
               productName = productName,
               price = price,
               quantity = 1
           ))

       }
   }


    private fun deleteCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepo.deleteCartItem(cartItem)
        }
    }
    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                CartViewModel(orderUkApplication().container.cardRepo)
            }
        }
    }
}