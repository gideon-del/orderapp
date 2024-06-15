package com.example.orderuk.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDAO {
    @Query("SELECT * FROM cart")
     fun  getAllCartItem() : Flow<List<CartItem>>
     @Insert(onConflict = OnConflictStrategy.IGNORE)
     suspend fun insertCartItem(cartItem: CartItem): Long

     @Update
     suspend fun upDateCartItem(cartItem: CartItem)

     @Delete
     suspend fun deleteCartItem(cartItem: CartItem)
}