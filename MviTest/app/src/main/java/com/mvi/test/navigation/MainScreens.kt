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

    @Serializable
    object BookHomeScreen : Screens()

    @Serializable
    data class BookDetailsScreen(val id: Int) : Screens()

    @Serializable
    data class BookManageScreen(val id: Int = -1) : Screens()
}

