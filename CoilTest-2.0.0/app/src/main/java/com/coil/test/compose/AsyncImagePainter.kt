package com.coil.test.compose

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import coil.ImageLoader
import com.coil.test.compose.AsyncImagePainter.Companion.DefaultTransform
import com.coil.test.compose.AsyncImagePainter.State
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.request.SuccessResult
import coil.size.Dimension
import coil.size.Precision
import coil.transition.CrossfadeTransition
import coil.transition.TransitionTarget
import com.google.accompanist.drawablepainter.DrawablePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import coil.size.Size as CoilSize

/**
 * Return an [AsyncImagePainter] that executes an [ImageRequest] asynchronously and renders the result.
 * 创建并返回一个 AsyncImagePainter 实例，用于异步加载图片并绘制
 *
 * **This is a lower-level API than [AsyncImage] and may not work as expected in all situations. **
 *
 * - [AsyncImagePainter] will not finish loading if [AsyncImagePainter.onDraw] is not called.
 *   This can occur if a composable has an unbounded (i.e. [Constraints.Infinity]) width/height
 *   constraint. For example, to use [AsyncImagePainter] with [LazyRow] or [LazyColumn], you must
 *   set a bounded width or height respectively using `Modifier.width` or `Modifier.height`.
 * - [AsyncImagePainter.state] will not transition to [State.Success] synchronously during the
 *   composition phase. Use [SubcomposeAsyncImage] or set a custom [ImageRequest.Builder.size] value
 *   (e.g. `size(Size.ORIGINAL)`) if you need this.
 *
 * @param model Either an [ImageRequest] or the [ImageRequest.data] value.
 * @param imageLoader The [ImageLoader] that will be used to execute the request.
 * @param placeholder A [Painter] that is displayed while the image is loading.
 * @param error A [Painter] that is displayed when the image request is unsuccessful.
 * @param fallback A [Painter] that is displayed when the request's [ImageRequest.data] is null.
 * @param onLoading Called when the image request begins loading.
 * @param onSuccess Called when the image request completes successfully.
 * @param onError Called when the image request completes unsuccessfully.
 * @param contentScale Used to determine the aspect ratio scaling to be used if the canvas bounds
 *  are a different size from the intrinsic size of the image loaded by [model]. This should be set
 *  to the same value that's passed to [Image].
 * @param filterQuality Sampling algorithm applied to a bitmap when it is scaled and drawn into the
 *  destination.
 */
@Composable
fun rememberAsyncImagePainter(
    model: Any?,
    imageLoader: ImageLoader,
    placeholder: Painter? = null,
    error: Painter? = null,
    fallback: Painter? = error,
    onLoading: ((State.Loading) -> Unit)? = null,
    onSuccess: ((State.Success) -> Unit)? = null,
    onError: ((State.Error) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Fit,
    filterQuality: FilterQuality = DefaultFilterQuality,
) = rememberAsyncImagePainter(
    model = model,
    imageLoader = imageLoader,
    transform = transformOf(placeholder, error, fallback),
    onState = onStateOf(onLoading, onSuccess, onError),
    contentScale = contentScale,
    filterQuality = filterQuality,
)

/**
 * Return an [AsyncImagePainter] that executes an [ImageRequest] asynchronously and renders the result.
 * 创建并返回一个 AsyncImagePainter 实例，用于异步加载图片并绘制
 *
 * **This is a lower-level API than [AsyncImage] and may not work as expected in all situations. **
 *
 * - [AsyncImagePainter] will not finish loading if [AsyncImagePainter.onDraw] is not called.
 *   This can occur if a composable has an unbounded (i.e. [Constraints.Infinity]) width/height
 *   constraint. For example, to use [AsyncImagePainter] with [LazyRow] or [LazyColumn], you must
 *   set a bounded width or height respectively using `Modifier.width` or `Modifier.height`.
 * - [AsyncImagePainter.state] will not transition to [State.Success] synchronously during the
 *   composition phase. Use [SubcomposeAsyncImage] or set a custom [ImageRequest.Builder.size] value
 *   (e.g. `size(Size.ORIGINAL)`) if you need this.
 *
 * @param model Either an [ImageRequest] or the [ImageRequest.data] value. 图片资源，可以是 URL、Uri、ImageRequest 等
 * @param imageLoader The [ImageLoader] that will be used to execute the request.
 * @param transform A callback to transform a new [State] before it's applied to the
 *  [AsyncImagePainter]. Typically this is used to overwrite the state's [Painter]. 状态转换器
 * @param onState Called when the state of this painter changes. 图片加载状态变化时的回调，例如从 Loading → Success
 * @param contentScale Used to determine the aspect ratio scaling to be used if the canvas bounds
 *  are a different size from the intrinsic size of the image loaded by [model]. This should be set
 *  to the same value that's passed to [Image]. 图片缩放方式
 * @param filterQuality Sampling algorithm applied to a bitmap when it is scaled and drawn into the
 *  destination. 图片缩放时的采样算法
 */
@Composable
fun rememberAsyncImagePainter(
    model: Any?,
    imageLoader: ImageLoader,
    transform: (State) -> State = DefaultTransform,
    onState: ((State) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Fit,
    filterQuality: FilterQuality = DefaultFilterQuality,
): AsyncImagePainter {
    val request = requestOf(model)
    validateRequest(request) // 检查请求是否合法

    val painter = remember { AsyncImagePainter(request, imageLoader) } // 记住 painter，避免重建
    painter.transform = transform // 状态转换器
    painter.onState = onState // 图片加载状态变化时的回调，例如从 Loading → Success
    painter.contentScale = contentScale // 图片缩放方式
    painter.filterQuality = filterQuality // 图片缩放时的采样算法
    painter.isPreview = LocalInspectionMode.current
    painter.imageLoader = imageLoader
    painter.request =
        request // Update request last so all other properties are up to date. // 最后才更新 request，确保其他字段已准备好
    painter.onRemembered() // Invoke this manually so `painter.state` is set to `Loading` immediately. // 手动调用，确保 painter.state 会变成 Loading
    return painter
}

/**
 * A [Painter] that that executes an [ImageRequest] asynchronously and renders the result.
 */
@Stable
class AsyncImagePainter internal constructor(
    request: ImageRequest,
    imageLoader: ImageLoader,
) : Painter(), RememberObserver {

    private var rememberScope: CoroutineScope? = null
    private val drawSize = MutableStateFlow(Size.Zero)

    private var painter: Painter? by mutableStateOf(null)
    private var alpha: Float by mutableStateOf(DefaultAlpha)
    private var colorFilter: ColorFilter? by mutableStateOf(null)

    // These fields allow access to the current value
    // instead of the value in the current composition.
    private var _state: State = State.Empty
        set(value) {
            field = value
            state = value
        }
    private var _painter: Painter? = null
        set(value) {
            field = value
            painter = value
        }

    internal var transform = DefaultTransform
    internal var onState: ((State) -> Unit)? = null
    internal var contentScale = ContentScale.Fit
    internal var filterQuality = DefaultFilterQuality
    internal var isPreview = false

    /** The current [AsyncImagePainter.State]. */
    var state: State by mutableStateOf(State.Empty)
        private set

    /** The current [ImageRequest]. */
    var request: ImageRequest by mutableStateOf(request)
        internal set

    /** The current [ImageLoader]. */
    var imageLoader: ImageLoader by mutableStateOf(imageLoader)
        internal set

    override val intrinsicSize: Size
        get() = painter?.intrinsicSize ?: Size.Unspecified

    override fun DrawScope.onDraw() {
        // Update the draw scope's current size.
        drawSize.value = size

        // Draw the current painter.
        painter?.apply { draw(size, alpha, colorFilter) }
    }

    override fun applyAlpha(alpha: Float): Boolean {
        this.alpha = alpha
        return true
    }

    override fun applyColorFilter(colorFilter: ColorFilter?): Boolean {
        this.colorFilter = colorFilter
        return true
    }

    /**
     * 核心生命周期方法之一
     * 启动一个协程，在作用域内监听 request 的变化并触发图片请求，然后更新 state，让 Compose 可以根据状态来重组界面（比如加载中、成功、失败等）
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onRemembered() {
        // Short circuit if we're already remembered.
        // 如果已经执行过（rememberScope 已存在），直接返回，避免重复初始化
        if (rememberScope != null) return

        // Create a new scope to observe state and execute requests while we're remembered.
        // 创建一个和 UI 生命周期绑定的 CoroutineScope，用于后续监听请求变化和执行加载任务。
        // SupervisorJob：允许子协程失败时不取消整个作用域
        // Dispatchers.Main.immediate：立即在主线程执行（用于 UI 相关操作）
        // 通过 + 组合成一个 CoroutineContext => SupervisorJob() 控制协程的生命周期，Dispatchers.Main.immediate 控制协程在哪个线程执行
        // HenCoder 协程 => coroutine Scope
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        rememberScope = scope

        // Manually notify the child painter that we're remembered.
        // 通知子级 painter（如果有）
        (_painter as? RememberObserver)?.onRemembered()

        // If we're in inspection mode skip the image request and set the state to loading.
        // 如果是预览模式，不发起网络请求，直接显示占位图（placeholder），并设置状态为 Loading。
        if (isPreview) {
            val request = request.newBuilder().defaults(imageLoader.defaults).build()
            updateState(State.Loading(request.placeholder?.toPainter()))
            return
        }

        // Observe the current request and execute any emissions.
        // 启动协程监听并加载图片
        scope.launch {
            // snapshotFlow { request }：监听 request 的状态变化（是一个 Compose State）
            // snapshotFlow => 会创建一个对内部 State 有感知的 Flow => 把 Compose 的 State 转换成协程 Flow
            // 使用场景 => flow 需要用到 State 的时候，用 snapshotFlow；会帮助你在 State 改变的时候触发 Flow 的更新
            snapshotFlow { request }
                // mapLatest { ... }：每次 request 变化时执行 imageLoader.execute(...)
                .mapLatest { imageLoader.execute(updateRequest(request)).toState() }
                .collect(::updateState)
        }
    }

    override fun onForgotten() {
        clear()
        (_painter as? RememberObserver)?.onForgotten()
    }

    override fun onAbandoned() {
        clear()
        (_painter as? RememberObserver)?.onAbandoned()
    }

    private fun clear() {
        rememberScope?.cancel()
        rememberScope = null
    }

    /** Update the [request] to work with [AsyncImagePainter]. */
    private fun updateRequest(request: ImageRequest): ImageRequest {
        return request.newBuilder().target(onStart = { placeholder ->
            updateState(State.Loading(placeholder?.toPainter()))
        }).apply {
            if (request.defined.sizeResolver == null) {
                // If no other size resolver is set, suspend until the canvas size is positive.
                size { drawSize.mapNotNull { it.toSizeOrNull() }.first() }
            }
            if (request.defined.scale == null) {
                // If no other scale resolver is set, use the content scale.
                scale(contentScale.toScale())
            }
            if (request.defined.precision != Precision.EXACT) {
                // AsyncImagePainter scales the image to fit the canvas size at draw time.
                precision(Precision.INEXACT)
            }
        }.build()
    }

    private fun updateState(input: State) {
        val previous = _state
        val current = transform(input)
        _state = current
        _painter = maybeNewCrossfadePainter(previous, current) ?: current.painter

        // Manually forget and remember the old/new painters if we're already remembered.
        if (rememberScope != null && previous.painter !== current.painter) {
            (previous.painter as? RememberObserver)?.onForgotten()
            (current.painter as? RememberObserver)?.onRemembered()
        }

        // Notify the state listener.
        onState?.invoke(current)
    }

    /** Create and return a [CrossfadePainter] if requested. */
    private fun maybeNewCrossfadePainter(previous: State, current: State): CrossfadePainter? {
        // We can only invoke the transition factory if the state is success or error.
        val result = when (current) {
            is State.Success -> current.result
            is State.Error -> current.result
            else -> return null
        }

        // Invoke the transition factory and wrap the painter in a `CrossfadePainter` if it returns
        // a `CrossfadeTransformation`.
        val transition = result.request.transitionFactory.create(FakeTransitionTarget, result)
        if (transition is CrossfadeTransition) {
            return CrossfadePainter(
                start = previous.painter.takeIf { previous is State.Loading },
                end = current.painter,
                contentScale = contentScale,
                durationMillis = transition.durationMillis,
                fadeStart = result !is SuccessResult || !result.isPlaceholderCached,
                preferExactIntrinsicSize = transition.preferExactIntrinsicSize
            )
        } else {
            return null
        }
    }

    private fun ImageResult.toState() = when (this) {
        is SuccessResult -> State.Success(drawable.toPainter(), this)
        is ErrorResult -> State.Error(drawable?.toPainter(), this)
    }

    /** Convert this [Drawable] into a [Painter] using Compose primitives if possible. */
    private fun Drawable.toPainter() = when (this) {
        is BitmapDrawable -> BitmapPainter(bitmap.asImageBitmap(), filterQuality = filterQuality)
        is ColorDrawable -> ColorPainter(Color(color))
        else -> DrawablePainter(mutate())
    }

    /**
     * The current state of the [AsyncImagePainter].
     */
    sealed class State {

        /** The current painter being drawn by [AsyncImagePainter]. */
        abstract val painter: Painter?

        /** The request has not been started. */
        object Empty : State() {
            override val painter: Painter? get() = null
        }

        /** The request is in-progress. */
        data class Loading(
            override val painter: Painter?,
        ) : State()

        /** The request was successful. */
        data class Success(
            override val painter: Painter,
            val result: SuccessResult,
        ) : State()

        /** The request failed due to [ErrorResult.throwable]. */
        data class Error(
            override val painter: Painter?,
            val result: ErrorResult,
        ) : State()
    }

    companion object {
        /**
         * A state transform that does not modify the state.
         */
        val DefaultTransform: (State) -> State = { it }
    }
}

/**
 * ImageBitmap 是 Compose 中的图像类型，它应该直接用 Image(bitmap = ...) 来显示
 * ImageVector 是矢量图（比如 Icons），同样不应该用 AsyncImagePainter 去加载。正确做法：Image(imageVector = ...)
 * Painter 是一个通用的绘制接口，比如你已经有了一个 painter，直接用 Image(painter = ...) 显示即可，不用再包一层 Coil。
 */
private fun validateRequest(request: ImageRequest) {
    when (request.data) {
        is ImageRequest.Builder -> unsupportedData(
            name = "ImageRequest.Builder",
            description = "Did you forget to call ImageRequest.Builder.build()?"
        )

        is ImageBitmap -> unsupportedData("ImageBitmap")
        is ImageVector -> unsupportedData("ImageVector")
        is Painter -> unsupportedData("Painter")
    }
    require(request.target == null) { "request.target must be null." }
}

/**
 * 开发时的防御机制，没有 try catch，会导致程序崩溃
 */
private fun unsupportedData(
    name: String,
    description: String = "If you wish to display this $name, use androidx.compose.foundation.Image.",
): Nothing = throw IllegalArgumentException("Unsupported type: $name. $description")

private val Size.isPositive get() = width >= 0.5 && height >= 0.5

private fun Size.toSizeOrNull() = when {
    isUnspecified -> CoilSize.ORIGINAL
    isPositive -> CoilSize(
        width = if (width.isFinite()) Dimension(width.roundToInt()) else Dimension.Undefined,
        height = if (height.isFinite()) Dimension(height.roundToInt()) else Dimension.Undefined
    )

    else -> null
}

private val FakeTransitionTarget = object : TransitionTarget {
    override val view get() = throw UnsupportedOperationException()
    override val drawable: Drawable? get() = null
}
