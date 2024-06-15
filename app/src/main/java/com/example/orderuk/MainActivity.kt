package com.example.orderuk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import com.google.firebase.Firebase
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.orderuk.domain.AuthViewModel
import com.example.orderuk.ui.navigation.AppNav
import com.example.orderuk.ui.navigation.AuthNav
import com.example.orderuk.ui.theme.OrderukTheme
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            OrderukTheme {
                var currentUser: FirebaseUser? by remember{
                    mutableStateOf(null)
                }
                LaunchedEffect(Unit) {
                    Firebase.auth.addAuthStateListener {auth->
                        currentUser = auth.currentUser
                    }
                }
                Surface(modifier = Modifier.background(Color.White).fillMaxSize()) {
                    if(currentUser === null){
                        AuthNav()
                    } else {
                        AppNav()
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OrderukTheme {
        Greeting("Android")
    }
}