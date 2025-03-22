package com.lottie.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.lottie.test.ui.theme.LottieTestTheme

/**
 * lottie 动画资源下载网站：https://lottiefiles.com/featured-free-animations
 * 原理：https://juejin.cn/post/6973551540850130952
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LottieTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SimpleLoader(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SimpleLoader(modifier: Modifier) {
    // 为什么要通过 rememberLottieComposition 来设置资源
    // 1. 性能优化：避免重复解析 JSON。昂贵的解析过程 + 缓存机制
    // 2. 异步加载：避免阻塞 UI 线程。后台解析 + 响应式状态管理
    // 3. 资源生命周期管理。自动释放资源 + 与 Compose 生命周期绑定
    // 4. 统一资源配置入口
    // 5. 错误处理与重试机制
    // 6. 与 Compose 生态深度集成
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.android)
    )
    LottieAnimation(
        composition,
        modifier = modifier.size(100.dp),
        iterations = LottieConstants.IterateForever, // 循环播放
//        clipSpec = LottieClipSpec.Progress(0.5f, 0.75f), // 片段播放（0.5-0.75区间）
        speed = 1.2f,
//        renderMode = RenderMode.HARDWARE, // 复杂动画
//        clipToCompositionBounds = true, // 长动画
    )
}