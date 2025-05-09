package com.mvi.test.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {
    @Serializable
    object SplashScreen : Screens()

    @Serializable
    object AuthScreen : Screens()

    @Serializable
    object MainScreen : Screens()

    @Serializable
    object WeatherScreen : Screens()

    @Serializable
    data class HetaScreen(val pointId: String) : Screens()
}

