package com.mvi.test.network

import com.mvi.test.Constants
import com.mvi.test.network.model.WeatherNow
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class WeatherApiImpl(
    private val httpClient: HttpClient
) : WeatherApi {

    override suspend fun getWeather(): WeatherNow {
        return httpClient.get(urlString = "v7/weather/now") {
            parameter("location", 101010100)
            parameter("key", Constants.API_KEY)
        }.body()
    }
}