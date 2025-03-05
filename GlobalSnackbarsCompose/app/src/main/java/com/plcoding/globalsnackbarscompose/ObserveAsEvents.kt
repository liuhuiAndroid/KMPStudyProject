package com.plcoding.globalsnackbarscompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

// 监听一个 Flow，并在生命周期 STARTED 时收集数据。
// 生命周期进入 STOPPED 时自动取消监听，避免内存泄漏。
// 事件的回调 onEvent 运行在主线程，确保 UI 线程安全。
@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit,
) {
    // 获取当前 Composable 所在的 LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current
    // 监听 lifecycleOwner.lifecycle、key1、key2 和 flow，如果任意一个发生变化，就会重新启动 LaunchedEffect
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2, flow) {
        // 仅当 生命周期处于 STARTED 及以上状态 时才会 收集 Flow
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // 确保 Flow 事件的回调 onEvent 始终在主线程执行，避免 UI 线程问题
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }
}