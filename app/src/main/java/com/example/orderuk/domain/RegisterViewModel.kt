package com.example.orderuk.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.orderuk.AppContainer
import com.example.orderuk.orderUkApplication
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel(private val auth: FirebaseAuth): ViewModel() {

    fun createAccount(email: String, password: String) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email,password).await()

        }

    }
    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                RegisterViewModel(orderUkApplication().container.auth)
            }
        }
    }
}