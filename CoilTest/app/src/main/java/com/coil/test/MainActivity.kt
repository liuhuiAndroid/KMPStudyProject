package com.coil.test

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.coil.test.ui.theme.CoilTestTheme

/**
 * https://juejin.cn/post/7403546034763235378
 * https://juejin.cn/post/7482949461564620811
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoilTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

private const val imageUrl =
    "https://tk.illlt.com:759/%E7%94%B5%E8%84%91%E7%AB%AF-%E4%BA%8C%E6%AC%A1%E5%85%83/MC%E9%85%B1%E5%8A%A8%E6%BC%AB/105.jpg"
private const val imageDescription = "MC酱动漫"

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        AsyncImage(
            model = imageUrl,
            contentDescription = imageDescription,
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)    // 淡入淡出动画效果
                .build(),
            contentDescription = imageDescription,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.book_error_2),
            error = painterResource(R.drawable.book_error_2),
            onLoading = {
                Log.i("MainActivity", "imageUrl onLoading")
            },
            onSuccess = {
                Log.i("MainActivity", "imageUrl onSuccess")
            },
            onError = {
                Log.i("MainActivity", "imageUrl onError")
            },
        )
        // 更加灵活和精细的 UI 控制，也可以直接使用 AsyncImage
        Box(
            modifier = Modifier.height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            var imageLoadResult by remember { mutableStateOf<Result<Painter>?>(null) }
            val painter = rememberAsyncImagePainter(
                model = imageUrl,
                onSuccess = {
                    imageLoadResult =
                        if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                            Result.success(it.painter)
                        } else {
                            Result.failure(Exception("Invalid image size"))
                        }
                },
                onError = {
                    it.result.throwable.printStackTrace()
                    imageLoadResult = Result.failure(it.result.throwable)
                }
            )

            val painterState by painter.state.collectAsStateWithLifecycle()
            val transition by animateFloatAsState(
                targetValue = if (painterState is AsyncImagePainter.State.Success) {
                    1f
                } else {
                    0f
                },
                animationSpec = tween(durationMillis = 800), label = ""
            )

            when (val result = imageLoadResult) {
                null -> PulseAnimation(
                    modifier = Modifier.size(60.dp)
                )

                else -> {
                    Image(
                        painter = if (result.isSuccess) painter else {
                            painterResource(R.drawable.book_error_2)
                        },
                        contentDescription = imageDescription,
                        contentScale = if (result.isSuccess) {
                            ContentScale.Crop
                        } else {
                            ContentScale.Fit
                        },
                        modifier = Modifier
                            .aspectRatio(
                                ratio = 0.65f,
                                matchHeightConstraintsFirst = true
                            )
                            .graphicsLayer {
                                rotationX = (1f - transition) * 30f
                                val scale = 0.8f + (0.2f * transition)
                                scaleX = scale
                                scaleY = scale
                            }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoilTestTheme {
        Greeting("Android")
    }
}