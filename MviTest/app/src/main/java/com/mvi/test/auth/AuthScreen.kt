package com.mvi.test.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mvi.test.navigation.MainScreens
import org.koin.androidx.compose.koinViewModel

/**
 * Mastering UI Events and States in Jetpack Compose ✅
 * https://www.youtube.com/watch?v=TzA049PAlSs
 */
@Composable
fun AuthScreen(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    viewModel: AuthViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.event.collectAsStateWithLifecycle(initialValue = UiEvent.Idle)

    LaunchedEffect(event) {
        when (event) {
            is UiEvent.Idle -> {}

            is UiEvent.Navigate -> {
                navController.navigate(MainScreens.HetaScreen.name)
            }

            is UiEvent.ShowSnackBar -> {
                snackbarHostState.showSnackbar((event as UiEvent.ShowSnackBar).message)
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.username,
            onValueChange = {
                viewModel.onEvent(UiIntent.EnterUsername(it))
            },
            label = { Text("Username") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.password,
            onValueChange = {
                viewModel.onEvent(UiIntent.EnterPassword(it))
            },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            modifier = Modifier.fillMaxWidth(), onClick = {
                viewModel.onEvent(UiIntent.ClickSignIn)
            }, enabled = !state.isLoading && state.isPasswordValid
        ) {
            AnimatedContent(
                targetState = state.isLoading
            ) { isLoading ->
                if (isLoading) {
                    // https://www.youtube.com/watch?v=Ji3hsOnh3es
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp), strokeWidth = 2.dp
                    )
                } else {
                    Text(text = "Sign in")
                }
            }
        }
    }
}