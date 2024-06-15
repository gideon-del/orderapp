package com.example.orderuk.domain

sealed class RestaurantState {
    data object Loading:RestaurantState()
    data class Error(val errorMessage: String): RestaurantState()
    data class Success (val dishes: List<Dishes>): RestaurantState()
}

data class Dishes(
    val name:String,
    val category: String,
    val description: String,
    val spiceLevel: Number,
    val image: String?,
    val sizes: Map<String, Number>
)