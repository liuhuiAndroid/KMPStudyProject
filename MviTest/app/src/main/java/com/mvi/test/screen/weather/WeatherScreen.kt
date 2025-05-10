package com.mvi.test.screen.weather

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

/**
 * https://www.youtube.com/watch?v=T4NGT9DL3qw
 * The Ultimate guide to MVI in Android
 */
@Composable
fun WeatherScreen(
    navController: NavHostController,
) {
    val navBackStackEntry = remember { navController.currentBackStackEntry!! }
    val viewModel: WeatherViewModel = viewModel(navBackStackEntry)

    val context = LocalContext.current
    val state by viewModel.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (state.weatherNow != null) {
                Text(
                    text = "Hello ${state.weatherNow}!"
                )
            }
            if (state.progressBar) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            viewModel.collectSideEffect { uiComponent ->
                when (uiComponent) {
                    is UIComponent.Toast -> {
                        Toast.makeText(context, uiComponent.text, Toast.LENGTH_SHORT)
                            .show()
                    }

                    is UIComponent.Dialog -> {}
                    is UIComponent.SnackBar -> {}
                }
            }
        }
    }
}