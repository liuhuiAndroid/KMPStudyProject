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
import com.ktor.test.ui.theme.KtorTestTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * https://juejin.cn/post/7396930610537168947
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

private val client: HttpClient by lazy {
    HttpClient(OkHttp.create()) {
        install(HttpTimeout) {
            socketTimeoutMillis = 30_000L       // 设置 socket 超时时间
            requestTimeoutMillis = 30_000L      // 设置请求超时时间
            connectTimeoutMillis = 30_000L      // 设置请求超时时间
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.i("network$$$==>", message)
                }
            }
            level = LogLevel.ALL
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            url {
                protocol = URLProtocol.HTTPS
                host = "www.juejin.cn"
            }
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
                    val response = client.get("")
                    Log.i("MainActivity", "Response: ${response.body<String>()}")
                }.onFailure {
                    Log.e("MainActivity", "Error: ${it.message}", it)
                }
            }
        }) {
            Text("发送网络请求")
        }
    }
}
