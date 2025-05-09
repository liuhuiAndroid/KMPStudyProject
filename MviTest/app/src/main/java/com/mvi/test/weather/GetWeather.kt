package com.mvi.test.weather

import com.mvi.test.network.WeatherApi
import com.mvi.test.network.model.WeatherNow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWeather(
    private val weatherApi: WeatherApi,
) {
    fun execute(): Flow<DataState<WeatherNow>> {
        return flow {
            emit(DataState.Loading(true))
            try {
                val weatherNow = weatherApi.getWeather()
                emit(DataState.Success(weatherNow))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(DataState.Error(UIComponent.Toast(e.message ?: "Unknown error")))
            } finally {
                emit(DataState.Loading(false))
            }
        }
    }

}