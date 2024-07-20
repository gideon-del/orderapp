package com.example.orderuk.domain

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.orderuk.data.CartContainer
import com.example.orderuk.data.CartItem
import com.example.orderuk.orderUkApplication
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date

class CartViewModel(private val cartRepo: CartContainer): ViewModel() {
     val uiState:Flow<List<CartItem>> = cartRepo.getCartItems()
    private val db = Firebase.firestore
fun onUIEvent(event: CartEvents){

    when(event) {
        is CartEvents.AddToCart -> addCartItem(event.cartItem)
        is CartEvents.DeleteCartItem -> deleteCartItem(event.cartItem)
        is CartEvents.UpdateCartItem -> updateCartItem(event.cartItem)
        is CartEvents.PlaceOrder -> placeOrder(carts = event.carts, shipment = event.shipment,cb=event.cb)
    }


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
    private fun placeOrder(carts: List<CartItem>, shipment: String, cb: () -> Unit) {
        viewModelScope.launch {
            try {
                val transformItem = object  {
                    val userId=Firebase.auth.currentUser?.uid
                    val shipment=shipment
                    val cart = carts.map { cartItem ->
                        object {
                            val productName=cartItem.productName
                            val productId = cartItem.productId
                            val imageUrl = cartItem.imageUrl
                            val quantity =cartItem.quantity
                            val size = cartItem.size
                            val price = cartItem.price
                        }
                    }
                    val createdAt= Date().time
                }
                db.collection("orders").add(transformItem).await()
                cartRepo.clearCart()
                cb()
            }catch (e:FirebaseException){
                Log.d("Add-Product","Add order failed")
                e.printStackTrace()
            }

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
