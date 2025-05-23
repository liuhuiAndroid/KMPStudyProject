package com.coil.test.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil.ImageLoader
import com.coil.test.compose.AsyncImagePainter.Companion.DefaultTransform
import com.coil.test.compose.AsyncImagePainter.State
import coil.request.ImageRequest

/**
 * A composable that executes an [ImageRequest] asynchronously and renders the result.
 *
 * @param model Either an [ImageRequest] or the [ImageRequest.data] value.
 * @param contentDescription Text used by accessibility services to describe what this image
 *  represents. This should always be provided unless this image is used for decorative purposes,
 *  and does not represent a meaningful action that a user can take.
 * @param imageLoader The [ImageLoader] that will be used to execute the request.
 * @param modifier Modifier used to adjust the layout algorithm or draw decoration content.
 * @param loading An optional callback to overwrite what's drawn while the image request is loading.
 * @param success An optional callback to overwrite what's drawn when the image request succeeds.
 * @param error An optional callback to overwrite what's drawn when the image request fails.
 * @param onLoading Called when the image request begins loading.
 * @param onSuccess Called when the image request completes successfully.
 * @param onError Called when the image request completes unsuccessfully.
 * @param alignment Optional alignment parameter used to place the [AsyncImagePainter] in the given
 *  bounds defined by the width and height.
 * @param contentScale Optional scale parameter used to determine the aspect ratio scaling to be
 *  used if the bounds are a different size from the intrinsic size of the [AsyncImagePainter].
 * @param alpha Optional opacity to be applied to the [AsyncImagePainter] when it is rendered
 *  onscreen.
 * @param colorFilter Optional [ColorFilter] to apply for the [AsyncImagePainter] when it is
 *  rendered onscreen.
 * @param filterQuality Sampling algorithm applied to a bitmap when it is scaled and drawn into the
 *  destination.
 */
@Composable
fun SubcomposeAsyncImage(
    model: Any?,
    contentDescription: String?,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
    loading: @Composable (SubcomposeAsyncImageScope.(State.Loading) -> Unit)? = null,
    success: @Composable (SubcomposeAsyncImageScope.(State.Success) -> Unit)? = null,
    error: @Composable (SubcomposeAsyncImageScope.(State.Error) -> Unit)? = null,
    onLoading: ((State.Loading) -> Unit)? = null,
    onSuccess: ((State.Success) -> Unit)? = null,
    onError: ((State.Error) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
) = SubcomposeAsyncImage(
    model = model,
    contentDescription = contentDescription,
    imageLoader = imageLoader,
    modifier = modifier,
    onState = onStateOf(onLoading, onSuccess, onError),
    alignment = alignment,
    contentScale = contentScale,
    alpha = alpha,
    colorFilter = colorFilter,
    filterQuality = filterQuality,
    content = contentOf(loading, success, error)
)

/**
 * A composable that executes an [ImageRequest] asynchronously and renders the result.
 *
 * @param model Either an [ImageRequest] or the [ImageRequest.data] value.
 * @param contentDescription Text used by accessibility services to describe what this image
 *  represents. This should always be provided unless this image is used for decorative purposes,
 *  and does not represent a meaningful action that a user can take.
 * @param imageLoader The [ImageLoader] that will be used to execute the request.
 * @param modifier Modifier used to adjust the layout algorithm or draw decoration content.
 * @param transform A callback to transform a new [State] before it's applied to the
 *  [AsyncImagePainter]. Typically this is used to modify the state's [Painter].
 * @param onState Called when the state of this painter changes.
 * @param alignment Optional alignment parameter used to place the [AsyncImagePainter] in the given
 *  bounds defined by the width and height.
 * @param contentScale Optional scale parameter used to determine the aspect ratio scaling to be
 *  used if the bounds are a different size from the intrinsic size of the [AsyncImagePainter].
 * @param alpha Optional opacity to be applied to the [AsyncImagePainter] when it is rendered
 *  onscreen.
 * @param colorFilter Optional [ColorFilter] to apply for the [AsyncImagePainter] when it is
 *  rendered onscreen.
 * @param filterQuality Sampling algorithm applied to a bitmap when it is scaled and drawn into the
 *  destination.
 * @param content A callback to draw the content inside an [SubcomposeAsyncImageScope].
 */
@Composable
fun SubcomposeAsyncImage(
    model: Any?,
    contentDescription: String?,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
    transform: (State) -> State = DefaultTransform,
    onState: ((State) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
    content: @Composable SubcomposeAsyncImageScope.() -> Unit,
) {
    // Create and execute the image request.
    // 构造 ImageRequest
    val request = updateRequest(requestOf(model), contentScale)
    // 用 rememberAsyncImagePainter() 创建 Painter，内部启动异步加载任务，并缓存
    val painter = rememberAsyncImagePainter(
        request, imageLoader, transform, onState, contentScale, filterQuality
    )

    // 判断是否需要 Subcompose（性能优化点）
    val sizeResolver = request.sizeResolver
    if (sizeResolver !is ConstraintsSizeResolver) {
        // Fast path: draw the content without subcomposition as we don't need to resolve the
        // constraints.
        // 如果请求中不依赖 layout constraints 计算图片大小（比如你直接写了 size(100, 100)），就可以走“快速路径”。
        // 使用普通的 Box 布局，不需要重新 subcompose。
        // 性能好，推荐！
        Box(
            modifier = modifier,
            contentAlignment = alignment,
            propagateMinConstraints = true
        ) {
            RealSubcomposeAsyncImageScope(
                parentScope = this,
                painter = painter,
                contentDescription = contentDescription,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter
            ).content()
        }
    } else {
        // Slow path: draw the content with subcomposition as we need to resolve the constraints
        // before calling `content`.
        // 如果 size 依赖布局约束（比如你用 .size(ViewSizeResolver(...))），就得用 BoxWithConstraints。
        // 这时需要先解析约束后再构建内容，所以用 SubcomposeLayout 实现。
        // 性能差一点，但必须这么做。
        // 间接地使用了 SubcomposeLayout => BoxWithConstraints
        BoxWithConstraints(
            modifier = modifier,
            contentAlignment = alignment,
            propagateMinConstraints = true
        ) {
            // Ensure `painter.state` is up to date immediately. Resolving the constraints
            // synchronously is necessary to ensure that images from the memory cache are resolved
            // and `painter.state` is updated to `Success` before invoking `content`.
            // <-- 在这步设置 constraints，BoxWithConstraints 的父组件给 BoxWithConstraints 的尺寸限制
            sizeResolver.setConstraints(constraints)

            RealSubcomposeAsyncImageScope(
                parentScope = this,
                painter = painter,
                contentDescription = contentDescription,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter
            ).content()
        }
    }
}

/**
 * A scope for the children of [com.coil.test.singleton.SubcomposeAsyncImage].
 *
 * extend BoxScope：提供了 Box 中可用的布局功能（如 align, matchParentSize 等）
 */
@LayoutScopeMarker // 标记这是一个布局作用域
@Immutable
interface SubcomposeAsyncImageScope : BoxScope {

    /** The painter that is drawn by [SubcomposeAsyncImageContent]. */
    val painter: AsyncImagePainter

    /** The content description for [SubcomposeAsyncImageContent]. */
    val contentDescription: String?

    /** The default alignment for any composables drawn in this scope. */
    val alignment: Alignment

    /** The content scale for [SubcomposeAsyncImageContent]. */
    val contentScale: ContentScale

    /** The alpha for [SubcomposeAsyncImageContent]. */
    val alpha: Float

    /** The color filter for [SubcomposeAsyncImageContent]. */
    val colorFilter: ColorFilter?
}

/**
 * A composable that draws [com.coil.test.singleton.SubcomposeAsyncImage]'s content with [SubcomposeAsyncImageScope]'s
 * properties.
 *
 * @see SubcomposeAsyncImageScope
 */
@Composable
fun SubcomposeAsyncImageScope.SubcomposeAsyncImageContent(
    modifier: Modifier = Modifier,
    painter: Painter = this.painter,
    contentDescription: String? = this.contentDescription,
    alignment: Alignment = this.alignment,
    contentScale: ContentScale = this.contentScale,
    alpha: Float = this.alpha,
    colorFilter: ColorFilter? = this.colorFilter,
) = Content(
    modifier = modifier,
    painter = painter,
    contentDescription = contentDescription,
    alignment = alignment,
    contentScale = contentScale,
    alpha = alpha,
    colorFilter = colorFilter
)

@Stable
private fun contentOf(
    loading: @Composable (SubcomposeAsyncImageScope.(State.Loading) -> Unit)?,
    success: @Composable (SubcomposeAsyncImageScope.(State.Success) -> Unit)?,
    error: @Composable (SubcomposeAsyncImageScope.(State.Error) -> Unit)?,
): @Composable SubcomposeAsyncImageScope.() -> Unit {
    return if (loading != null || success != null || error != null) {
        {
            var draw = true
            when (val state = painter.state) {
                is State.Loading -> if (loading != null) loading(state).also { draw = false }
                is State.Success -> if (success != null) success(state).also { draw = false }
                is State.Error -> if (error != null) error(state).also { draw = false }
                is State.Empty -> {} // Skipped if rendering on the main thread.
            }
            if (draw) SubcomposeAsyncImageContent()
        }
    } else {
        { SubcomposeAsyncImageContent() }
    }
}

/**
 * BoxScope by parentScope 接口委托，表示将 BoxScope 的所有函数“委托”给 parentScope 实例。
 */
private data class RealSubcomposeAsyncImageScope(
    private val parentScope: BoxScope,
    override val painter: AsyncImagePainter,
    override val contentDescription: String?,
    override val alignment: Alignment,
    override val contentScale: ContentScale,
    override val alpha: Float,
    override val colorFilter: ColorFilter?,
) : SubcomposeAsyncImageScope, BoxScope by parentScope
