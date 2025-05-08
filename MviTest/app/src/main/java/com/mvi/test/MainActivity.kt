package com.mvi.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.mvi.test.auth.AuthScreen
import com.mvi.test.auth.AuthViewModel
import com.mvi.test.ui.theme.MviTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel by viewModels<AuthViewModel>()
        setContent {
            MviTestTheme {
                AuthScreen(viewModel)
            }
        }
    }
}