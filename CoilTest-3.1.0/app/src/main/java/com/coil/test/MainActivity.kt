package com.coil.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import coil3.compose.SubcomposeAsyncImage
import com.coil.test.ui.theme.CoilTestTheme

/**
 * https://juejin.cn/post/7403546034763235378
 * https://juejin.cn/post/7482949461564620811
 * https://juejin.cn/post/6897872882051842061
 *
 * https://github.com/coil-kt/coil
 * https://coil-kt.github.io/coil
 * https://www.youtube.com/watch?v=qQVCtkg-O7w
 */
class MainActivity : ComponentActivity() {

    private val imageUrl =
        "https://tk.illlt.com:759/%E7%94%B5%E8%84%91%E7%AB%AF-%E4%BA%8C%E6%AC%A1%E5%85%83/MC%E9%85%B1%E5%8A%A8%E6%BC%AB/105.jpg"
    private val imageDescription = "MC酱动漫"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoilTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SubcomposeAsyncImage(
                        model = imageUrl,
                        contentDescription = imageDescription,
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        loading = {
                            CircularProgressIndicator()
                        },
                    )
                }
            }
        }
    }
}
