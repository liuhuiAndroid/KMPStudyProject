package com.mvi.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.mvi.test.auth.AuthScreen
import com.mvi.test.auth.AuthViewModel
import com.mvi.test.ui.theme.MviTestTheme

/**
 * https://www.youtube.com/watch?v=T4NGT9DL3qw  The Ultimate guide to MVI in Android
 * https://www.youtube.com/watch?v=TzA049PAlSs  Mastering UI Events and States in Jetpack Compose ✅
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        val viewModel by viewModels<WeatherViewModel>()
        val viewModel by viewModels<AuthViewModel>()

        setContent {
            MviTestTheme {
//                WeatherScreen(viewModel)
                AuthScreen(viewModel) {

                }
            }
        }
    }
}