package com.example.orderuk.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItem (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val quantity: Int,
    val price:Int,
    val productName: String,
    val imageUrl: String,
    val productId: String,
    val size: String
)