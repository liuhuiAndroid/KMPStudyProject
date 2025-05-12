package com.mvi.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mvi.test.navigation.MainNavigation
import com.mvi.test.ui.theme.MviTestTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MviTestTheme {
                MainNavigation()
            }
        }
    }
}