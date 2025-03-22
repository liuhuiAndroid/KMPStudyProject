package com.mvi.test.network

import android.util.Log
import com.mvi.test.Constants
import com.mvi.test.network.model.WeatherNow
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface WeatherApi {
    suspend fun getWeather(): WeatherNow

    companion object {
        private const val NETWORK_TIME_OUT = 30_000L

        private val httpClient = HttpClient(OkHttp.create()) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })     // 忽略未知字段，避免解析错误
            }
            install(HttpTimeout) {
                socketTimeoutMillis = NETWORK_TIME_OUT       // 设置 socket 超时时间
                requestTimeoutMillis = NETWORK_TIME_OUT      // 设置请求超时时间
                connectTimeoutMillis = NETWORK_TIME_OUT      // 设置请求超时时间
            }
            // 启用日志功能
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.i("MainActivity", "network$$$==>$message")
                    }
                }
                level = LogLevel.ALL
            }
//            install(Auth) {
//                bearer {
//                    loadTokens {  }
//                    refreshTokens {  }
//                }
//            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }

            // 设置所有请求的默认配置
            defaultRequest {
                contentType(ContentType.Application.Json)
                url(Constants.BASE_URL)
            }
        }

        fun provideWeatherApi(): WeatherApi = WeatherApiImpl(httpClient)
    }
}