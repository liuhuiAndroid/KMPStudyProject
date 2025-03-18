package com.ktor.test

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ktor.test.model.Weather24h
import com.ktor.test.model.Weather7d
import com.ktor.test.model.WeatherNow
import com.ktor.test.ui.theme.KtorTestTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

/**
 * Ktor网络框架源码分析 https://juejin.cn/post/7396930610537168947
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KtorTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android", modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

private val httpClient: HttpClient by lazy {
    HttpClient(OkHttp.create()) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })     // 忽略未知字段，避免解析错误
        }
        install(HttpTimeout) {
            socketTimeoutMillis = 30_000L       // 设置 socket 超时时间
            requestTimeoutMillis = 30_000L      // 设置请求超时时间
            connectTimeoutMillis = 30_000L      // 设置请求超时时间
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
        // 设置所有请求的默认配置
        defaultRequest {
            contentType(ContentType.Application.Json)
            url(Constants.BASE_URL)
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name!", modifier = modifier
        )
        Button({
            GlobalScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    Log.i("MainActivity", "Send message.")
                    val response = httpClient.get(urlString = "v7/weather/now") {
                        parameter("location", 101010100)
                        parameter("key", Constants.API_KEY)
                    }
                    Log.i("MainActivity", "Response: ${response.body<WeatherNow>()}")
                }.onFailure {
                    Log.e("MainActivity", "Error: ${it.message}", it)
                }
            }
        }) {
            Text("请求实时天气")
        }
        Button({
            GlobalScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    Log.i("MainActivity", "Send message.")
                    val response = httpClient.get(urlString = "v7/weather/7d") {
                        parameter("location", 101010100)
                        parameter("key", Constants.API_KEY)
                    }
                    Log.i("MainActivity", "Response: ${response.body<Weather7d>()}")
                }.onFailure {
                    Log.e("MainActivity", "Error: ${it.message}", it)
                }
            }
        }) {
            Text("请求每日天气预报/7天预报")
        }
        Button({
            GlobalScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    Log.i("MainActivity", "Send message.")
                    val response = httpClient.get(urlString = "v7/weather/24h") {
                        parameter("location", 101010100)
                        parameter("key", Constants.API_KEY)
                    }
                    Log.i("MainActivity", "Response: ${response.body<Weather24h>()}")
                }.onFailure {
                    Log.e("MainActivity", "Error: ${it.message}", it)
                }
            }
        }) {
            Text("请求逐小时天气预报/未来24小时")
        }
    }
}
