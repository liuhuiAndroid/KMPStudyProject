package com.destinations.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.destinations.test.core.di.DependencyContainer
import com.destinations.test.ui.theme.ComposeDestinationsTestTheme

val LocalDependencyContainer = staticCompositionLocalOf<DependencyContainer> {
    error("No dependency container provided!")
}

/**
 * https://github.com/raamcosta/compose-destinations
 */
class MainActivity : ComponentActivity() {

    private val dependencyContainer by lazy { DependencyContainer(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeDestinationsTestTheme {
                CompositionLocalProvider(LocalDependencyContainer provides dependencyContainer) {
                    SampleApp()
                }
            }
        }
    }
}
