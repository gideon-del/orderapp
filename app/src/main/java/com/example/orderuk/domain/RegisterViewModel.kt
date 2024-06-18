package com.example.orderuk.domain

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.orderuk.R
import com.example.orderuk.data.CartContainer
import com.example.orderuk.domain.use_cases.ValidateEmail
import com.example.orderuk.domain.use_cases.ValidatePassword

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(
    private val emailValidator: ValidateEmail = ValidateEmail(),
    private val passwordValidator: ValidatePassword = ValidatePassword()
) : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private var _uiState = MutableStateFlow(RegisterState())
    val uiState = _uiState.asStateFlow()
    var currentUser = flow<FirebaseUser?> {
        val user = auth.currentUser
        emit(user)
    }.catch {e->
        e.printStackTrace()
        if(e is CancellationException) throw e
    }
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
    fun signInAccount(context: Context,cb: () -> Unit={}) {
        viewModelScope.launch {
            if( checkValidation()){

                try {
                    auth.signInWithEmailAndPassword(_uiState.value.email, _uiState.value.password).await()
                }catch(e: FirebaseAuthException){

                    Toast.makeText(context,context.getString(R.string.register_error), Toast.LENGTH_LONG).show()
                }
                cb()
            } else{
                _uiState.value = _uiState.value.copy(
                    firstTime = false
                )
            }
        }

    }
    fun createAccount(context: Context, cb: () -> Unit = {}) {

        viewModelScope.launch {

            if( checkValidation()){
                try {
                  auth.createUserWithEmailAndPassword(_uiState.value.email, _uiState.value.password).await()
                }catch(e: FirebaseAuthException){
                    Toast.makeText(context,context.getString(R.string.register_error), Toast.LENGTH_LONG).show()
                }


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
                AuthViewModel()
            }
        }
    }
}