package com.example.orderuk.domain

import android.util.Log
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
        is CartEvents.AddToCart -> addCartItem(event.cartItem)
        is CartEvents.DeleteCartItem -> deleteCartItem(event.cartItem)
        is CartEvents.UpdateCartItem -> updateCartItem(event.cartItem)
    }
}
  private fun addCartItem(cartItem: CartItem){
       viewModelScope.launch {


          val isAdded = cartRepo.insertCartItem(cartItem)
           Log.d("Product", isAdded.toString())

       }
   }


    private fun deleteCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepo.deleteCartItem(cartItem)
        }
    }
    private fun updateCartItem  (cartItem: CartItem) {
        viewModelScope.launch {
            cartRepo.updateCartItem(cartItem)
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