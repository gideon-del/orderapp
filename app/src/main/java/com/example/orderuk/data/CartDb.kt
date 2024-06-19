package com.example.orderuk.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartItem::class], version = 4, exportSchema = false)
abstract class CartDb() : RoomDatabase() {
    abstract fun cartDAO(): CartDAO

    companion object {
        @Volatile
        private var Instance: CartDb? = null
        fun getDatabase(context: Context): CartDb {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CartDb::class.java, "cart_db")
                    .fallbackToDestructiveMigration().build().also {
                    Instance = it
                }
            }
        }
    }
}