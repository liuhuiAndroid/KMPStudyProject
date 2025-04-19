package com.coil.test.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Constraints
import coil.ImageLoader
import com.coil.test.compose.AsyncImagePainter.Companion.DefaultTransform
import com.coil.test.compose.AsyncImagePainter.State
import coil.request.ImageRequest
import coil.size.Dimension
import coil.size.SizeResolver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapNotNull
import coil.size.Size as CoilSize

/**
 * A composable that executes an [ImageRequest] asynchronously and renders the result.
 *
 * @param model Either an [ImageRequest] or the [ImageRequest.data] value.
 * @param contentDescription Text used by accessibility services to describe what this image
 *  represents. This should always be provided unless this image is used for decorative purposes,
 *  and does not represent a meaningful action that a user can take.
 * @param imageLoader The [ImageLoader] that will be used to execute the request.
 * @param modifier Modifier used to adjust the layout algorithm or draw decoration content.
 * @param placeholder A [Painter] that is displayed while the image is loading.
 * @param error A [Painter] that is displayed when the image request is unsuccessful.
 * @param fallback A [Painter] that is displayed when the request's [ImageRequest.data] is null.
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
fun AsyncImage(
    model: Any?,
    contentDescription: String?,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    error: Painter? = null,
    fallback: Painter? = error,
    onLoading: ((State.Loading) -> Unit)? = null,
    onSuccess: ((State.Success) -> Unit)? = null,
    onError: ((State.Error) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
) = AsyncImage(
    model = model,
    contentDescription = contentDescription,
    imageLoader = imageLoader,
    modifier = modifier,
    transform = transformOf(placeholder, error, fallback),
    onState = onStateOf(onLoading, onSuccess, onError),
    alignment = alignment,
    contentScale = contentScale,
    alpha = alpha,
    colorFilter = colorFilter,
    filterQuality = filterQuality
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
 */
@Composable
fun AsyncImage(
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
) {
    // Create and execute the image request.
    // 构建并更新 ImageRequest => 构造最终将要执行的异步加载请求
    val request = updateRequest(requestOf(model), contentScale)
    val painter = rememberAsyncImagePainter(
        request, imageLoader, transform, onState, contentScale, filterQuality
    )

    // Draw the content without a parent composable or subcomposition.
    // ConstraintsSizeResolver 是 Coil 的一个机制，用于根据父布局的约束动态设置图片请求尺寸（避免加载超大图）。
    // 如果使用的是 ConstraintsSizeResolver，就把它追加到 Modifier 中，以便它能获取父布局的大小信息。
    val sizeResolver = request.sizeResolver
    Content(
        modifier = if (sizeResolver is ConstraintsSizeResolver) {
            modifier.then(sizeResolver)
        } else {
            modifier
        },
        painter = painter,
        contentDescription = contentDescription,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter
    )
}

/**
 * Draws the current image content.
 * @param modifier
 * @param painter
 * @param contentDescription
 * @param alignment
 * @param contentScale 缩放方式
 * @param alpha 透明度
 * @param colorFilter 滤镜
 */
@Composable
internal fun Content(
    modifier: Modifier,
    painter: Painter,
    contentDescription: String?,
    alignment: Alignment,
    contentScale: ContentScale,
    alpha: Float,
    colorFilter: ColorFilter?,
) = Layout( // 视频 => 7. 自定义布局和 Layout() => 打脸朱凯不带 content 的 Layout 没什么用
    // 本身不绘制内容，真正的绘图是交给 Modifier 来做
    // 为什么要用 Layout + Modifier 而不是直接 Image() => 为了实现更细粒度的控制
    // - 动态决定是否绘图（比如等图片加载成功后才绘制）
    // - 支持动态状态管理（通过 Painter 的状态）
    // - 可组合地支持自定义 Painter 的绘制行为
    // - 更灵活地嵌入自定义动画、滤镜等效果
    modifier = modifier
        .contentDescription(contentDescription)
        .clipToBounds() // 确保绘图不超出布局边界
        .then(
            // ContentPainterModifier 是一个自定义的 DrawModifier，它会调用 painter.draw(...) 来绘制图片
            ContentPainterModifier(
                painter = painter,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter
            )
        ),
    measurePolicy = { _, constraints ->
        layout(constraints.minWidth, constraints.minHeight) {}
    }
)

/**
 * 根据 ContentScale 和当前 ImageRequest 的状态，决定是否需要为请求设置一个合适的 SizeResolver（大小解析器）
 * @param contentScale 表示 Compose 中用于控制图片如何缩放填充容器的枚举
 */
@Composable
internal fun updateRequest(request: ImageRequest, contentScale: ContentScale): ImageRequest {
    return if (request.defined.sizeResolver == null) {
        val sizeResolver = if (contentScale == ContentScale.None) {
            SizeResolver(CoilSize.ORIGINAL)
        } else {
            remember { ConstraintsSizeResolver() }
        }
        request.newBuilder().size(sizeResolver).build()
    } else {
        request
    }
}

/**
 * A [SizeResolver] that computes the size from the constrains passed during the layout phase.
 * 根据布局阶段传入的约束（constraints）来计算尺寸
 */
internal class ConstraintsSizeResolver : SizeResolver, LayoutModifier {

    private val _constraints = MutableStateFlow(ZeroConstraints)

    override suspend fun size() = _constraints.mapNotNull(Constraints::toSizeOrNull).first()

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        // Cache the current constraints.
        _constraints.value = constraints

        // Measure and layout the content.
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    }

    fun setConstraints(constraints: Constraints) {
        _constraints.value = constraints
    }
}

/**
 * 给无障碍服务提供图片的文字描述
 */
@Stable
private fun Modifier.contentDescription(contentDescription: String?): Modifier {
    if (contentDescription != null) {
        return semantics {
            this.contentDescription = contentDescription
            this.role = Role.Image
        }
    } else {
        return this
    }
}

@Stable
private fun Constraints.toSizeOrNull() = when {
    isZero -> null
    else -> CoilSize(
        width = if (hasBoundedWidth) Dimension(maxWidth) else Dimension.Undefined,
        height = if (hasBoundedHeight) Dimension(maxHeight) else Dimension.Undefined
    )
}
