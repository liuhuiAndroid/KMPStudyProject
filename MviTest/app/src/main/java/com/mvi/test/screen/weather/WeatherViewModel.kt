package com.mvi.test.screen.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.test.network.WeatherApi
import com.mvi.test.network.model.WeatherNow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

data class WeatherState(
    val progressBar: Boolean = false,
    val weatherNow: WeatherNow? = null,
    val error: String? = null,
)

class WeatherViewModel : ViewModel(), ContainerHost<WeatherState, UIComponent> {

    override val container: Container<WeatherState, UIComponent> = container(WeatherState())

    private val getWeather = GetWeather(WeatherApi.provideWeatherApi())

    init {
        getWeather()
    }

    private fun getWeather() {
        intent {
            val weather = getWeather.execute()
            weather.onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        reduce {
                            state.copy(progressBar = dataState.isLoading)
                        }
                    }

                    is DataState.Success -> {
                        reduce {
                            state.copy(weatherNow = dataState.data)
                        }
                    }

                    is DataState.Error -> {
                        when (dataState.uiComponent) {
                            is UIComponent.Toast -> {
                                postSideEffect(UIComponent.Toast(dataState.uiComponent.text))
                            }

                            else -> {}
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}