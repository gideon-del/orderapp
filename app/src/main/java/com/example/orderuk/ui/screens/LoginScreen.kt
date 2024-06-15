package com.example.orderuk.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.orderuk.R
import com.example.orderuk.domain.AuthViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    registerViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory),
    navigsteToRegister:() -> Unit={}
) {
    val context = LocalContext.current
    var showPassword by remember {
        mutableStateOf(false)
    }
    val screenState by registerViewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(
                    id = R.string.app_name
                ),

                )
        }
        Spacer(modifier = modifier.height(20.dp))
        OutlinedTextField(
            value = screenState.email,
            onValueChange = {
                registerViewModel.updateEmail(it)
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = !screenState.firstTime && !screenState.emailResult.successful,
            label = {
                Text("Email")
            }
        )
        if (!screenState.emailResult.successful && !screenState.firstTime) {
            Text(
                text = screenState.emailResult.errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = modifier.height(10.dp))
        OutlinedTextField(
            value = screenState.password,
            onValueChange = {
                registerViewModel.updatePassword(it)
            },
            singleLine = true,
            visualTransformation = if (!showPassword) PasswordVisualTransformation() else VisualTransformation.None,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            isError = !screenState.firstTime && !screenState.passwordResult.successful,
            label = {
                Text("Password")
            }
        )
        if (!screenState.passwordResult.successful && !screenState.firstTime) {
            Text(
                text = screenState.passwordResult.errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = modifier.height(10.dp))
        Button(
            onClick = {
                registerViewModel.signInAccount(context)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 13.dp)
                .clip(RoundedCornerShape(9.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFC8A06),
                contentColor = Color.White
            ),

            ) {
            Text(text = stringResource(id = R.string.login))
        }
        Button(
            onClick = {
                      navigsteToRegister()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xFFFC8A06),
                containerColor = Color.Transparent
            ),

            ) {
            Text(text = stringResource(id = R.string.create_account))
        }
    }
}