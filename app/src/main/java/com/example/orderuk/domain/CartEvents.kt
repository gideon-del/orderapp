package com.example.orderuk.domain

import com.example.orderuk.data.CartItem

sealed class CartEvents {
    data class AddToCart(val cartItem: CartItem): CartEvents()
    data class DeleteCartItem(val cartItem: CartItem): CartEvents()
    data class UpdateCartItem(val cartItem: CartItem): CartEvents()
}