package com.example.orderuk.data

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

interface AppContainer {
    val cardRepo : CartContainer

}



class CartContainer(private val cartDAO: CartDAO, val auth: FirebaseAuth = Firebase.auth) {
    fun getCartItems() = cartDAO.getAllCartItem()
    suspend fun insertCartItem(cartItem: CartItem) = cartDAO.insertCartItem(cartItem)
    suspend fun updateCartItem(cartItem: CartItem) = cartDAO.upDateCartItem(cartItem)

    suspend fun deleteCartItem(cartItem: CartItem)  = cartDAO.deleteCartItem(cartItem)
    suspend fun clearCart() = cartDAO.clearCart()
}

class DefaultContainer(private val cartDAO: CartDAO): AppContainer{
    override val cardRepo: CartContainer= CartContainer(cartDAO)
}