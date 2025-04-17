// Kotlin 文件级注解，用于 抑制编译器的警告
@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.coil.test.singleton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.imageLoader
import com.coil.test.compose.ImagePainter
import com.coil.test.compose.ImagePainter.ExecuteCallback
import com.coil.test.compose.rememberImagePainter
import coil.request.ImageRequest

/**
 * Return an [ImagePainter] that will execute an [ImageRequest] using [LocalImageLoader].
 *
 * @param data The [ImageRequest.data] to load. 图片资源（如 URL、资源 ID、本地文件等）
 * @param onExecute Called immediately before the [ImagePainter] launches an image request.
 *  Return 'true' to proceed with the request. Return 'false' to skip executing the request.
 * @param builder An optional lambda to configure the request. 用户可设置请求参数，比如 placeholder、crossfade 等
 */
@Composable
inline fun rememberImagePainter(
    data: Any?,
    onExecute: ExecuteCallback = ExecuteCallback.Default,
    builder: ImageRequest.Builder.() -> Unit = {},
): ImagePainter = rememberImagePainter(data, LocalImageLoader.current, onExecute, builder)

/**
 * Return an [ImagePainter] that will execute the [request] using [LocalImageLoader].
 *
 * @param request The [ImageRequest] to execute.
 * @param onExecute Called immediately before the [ImagePainter] launches an image request.
 *  Return 'true' to proceed with the request. Return 'false' to skip executing the request.
 */
@Composable
inline fun rememberImagePainter(
    request: ImageRequest,
    onExecute: ExecuteCallback = ExecuteCallback.Default,
): ImagePainter = rememberImagePainter(request, LocalImageLoader.current, onExecute)

/**
 * A pseudo-[CompositionLocal] that returns the current [ImageLoader] for the composition.
 * If a local [ImageLoader] has not been provided, it returns the singleton [ImageLoader].
 *
 * 这是一个 CompositionLocal，作用是提供当前 Compose 作用域下的 ImageLoader 实例
 */
val LocalImageLoader = ImageLoaderProvidableCompositionLocal()

/**
 * @see LocalImageLoader 提供上下文感知的 ImageLoader
 *
 * @JvmInline + value class：这是一个 轻量的内联类，不分配额外对象，性能更好
 */
@JvmInline
value class ImageLoaderProvidableCompositionLocal internal constructor(
    // 通过 staticCompositionLocalOf 创建了一个 CompositionLocal<ImageLoader?> 实例，初始值是 null
    // val LocalImageLoaderInternal = staticCompositionLocalOf<ImageLoader?> { null }
    private val delegate: ProvidableCompositionLocal<ImageLoader?> = staticCompositionLocalOf { null },
) {

    // LocalContext.current.imageLoader 是 Coil 提供的应用级别的单例 ImageLoader
    // delegate.current：尝试获取当前作用域中是否通过 CompositionLocalProvider 显式提供了 ImageLoader
    // 如果没有提供（即为 null），就使用 LocalContext.current.imageLoader —— 这是 Coil 默认提供的 App 级别的单例
    val current: ImageLoader
        @Composable get() = delegate.current ?: LocalContext.current.imageLoader

    // Kotlin infix（中缀）函数，用来在 CompositionLocalProvider 中设置值
    infix fun provides(value: ImageLoader) = delegate provides value

    // Kotlin infix（中缀）函数，用来在 CompositionLocalProvider 中设置值
    infix fun providesDefault(value: ImageLoader) = delegate providesDefault value
}
