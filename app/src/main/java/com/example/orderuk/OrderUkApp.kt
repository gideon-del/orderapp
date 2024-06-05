package com.example.orderuk

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

interface AppContainer{
    val auth: FirebaseAuth
}
class DefaultContainer(override val auth: FirebaseAuth): AppContainer {

}
class OrderUkApp:Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer(Firebase.auth)
    }
}

fun CreationExtras.orderUkApplication(): OrderUkApp = this[APPLICATION_KEY] as OrderUkApp