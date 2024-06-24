package com.example.orderuk.domain

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.orderuk.data.CartContainer
import com.example.orderuk.data.CartItem
import com.example.orderuk.orderUkApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CartViewModel(private val cartRepo: CartContainer): ViewModel() {
     val uiState:Flow<List<CartItem>> = cartRepo.getCartItems()

fun onUIEvent(event: CartEvents?): Flow<List<CartItem>>{

    when(event) {
        is CartEvents.AddToCart -> addCartItem(event.cartItem)
        is CartEvents.DeleteCartItem -> deleteCartItem(event.cartItem)
        is CartEvents.UpdateCartItem -> updateCartItem(event.cartItem)
        else -> {}
    }
return uiState

}
  private fun addCartItem(cartItem: CartItem){
       viewModelScope.launch {



            cartRepo.insertCartItem(cartItem)




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