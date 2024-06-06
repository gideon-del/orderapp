package com.example.orderuk.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.orderuk.AppContainer
import com.example.orderuk.domain.use_cases.ValidateEmail
import com.example.orderuk.domain.use_cases.ValidatePassword
import com.example.orderuk.orderUkApplication
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(
    private val auth: FirebaseAuth,
    private val emailValidator: ValidateEmail = ValidateEmail(),
    private val passwordValidator: ValidatePassword = ValidatePassword()
) : ViewModel() {
    private var _uiState = MutableStateFlow(RegisterState())
    val uiState = _uiState.asStateFlow()
    fun updateEmail(email: String) {

        _uiState.value = _uiState.value.copy(
            email = email,
            emailResult = emailValidator.validate(email)
        )
    }
    fun updatePassword(password: String){
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordResult = passwordValidator.validate(password)
        )
    }
    private fun checkValidation(): Boolean {
      return  _uiState.value.emailResult.successful && _uiState.value.passwordResult.successful
    }
    fun signInAccount(cb: () -> Unit={}) {
        viewModelScope.launch {
            if( checkValidation()){
                auth.signInWithEmailAndPassword(_uiState.value.email, _uiState.value.password).await()
                cb()
            } else{
                _uiState.value = _uiState.value.copy(
                    firstTime = false
                )
            }
        }

    }
    fun createAccount( cb: () -> Unit = {}) {

        viewModelScope.launch {

            if( checkValidation()){
                auth.createUserWithEmailAndPassword(_uiState.value.email, _uiState.value.password).await()
                cb()
            } else{
                _uiState.value = _uiState.value.copy(
                    firstTime = false
                )
            }


        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AuthViewModel(orderUkApplication().container.auth)
            }
        }
    }
}