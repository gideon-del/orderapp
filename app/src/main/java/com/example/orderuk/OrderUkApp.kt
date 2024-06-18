package com.example.orderuk

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.orderuk.data.AppContainer
import com.example.orderuk.data.CartDb
import com.example.orderuk.data.DefaultContainer
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class OrderUkApp:Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer(CartDb.getDatabase(this).cartDAO())
    }
}

fun CreationExtras.orderUkApplication(): OrderUkApp = this[APPLICATION_KEY] as OrderUkApp