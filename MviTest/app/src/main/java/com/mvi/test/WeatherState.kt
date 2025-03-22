package com.mvi.test

import com.mvi.test.network.model.WeatherNow

data class WeatherState(
    val progressBar: Boolean = false,
    val weatherNow: WeatherNow? = null,
    val error: String? = null,
)