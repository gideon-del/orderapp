package com.example.orderuk.domain

import com.example.orderuk.data.CartItem

sealed class CartEvents {
    data class AddToCart(val productName: String, val price: Int): CartEvents()
    data class DeleteCartItem(val cartItem: CartItem): CartEvents()
}