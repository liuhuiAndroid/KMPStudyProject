// 即使代码中使用了已弃用的 API，也不要报警告
@file:Suppress("DEPRECATION")

package com.coil.test.singleton

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.coil.test.compose.AsyncImagePainter.Companion.DefaultTransform
import com.coil.test.compose.AsyncImagePainter.State
import coil.request.ImageRequest
import com.coil.test.compose.AsyncImage

/**
 * A composable that executes an [ImageRequest] asynchronously and renders the result.
 *
 * @param model Either an [ImageRequest] or the [ImageRequest.data] value.
 * @param contentDescription Text used by accessibility services to describe what this image
 *  represents. This should always be provided unless this image is used for decorative purposes,
 *  and does not represent a meaningful action that a user can take. 给无障碍服务使用的描述文字
 * @param modifier Modifier used to adjust the layout algorithm or draw decoration content.
 * @param placeholder A [Painter] that is displayed while the image is loading. 图片加载中时显示的占位图
 * @param error A [Painter] that is displayed when the image request is unsuccessful. 加载失败时显示的图片
 * @param fallback A [Painter] that is displayed when the request's [ImageRequest.data] is null. 如果 model 是 null，则显示这个图片（默认等于 error）
 * @param onLoading Called when the image request begins loading. 加载中回调
 * @param onSuccess Called when the image request completes successfully. 成功回调
 * @param onError Called when the image request completes unsuccessfully. 失败回调
 * @param alignment Optional alignment parameter used to place the [com.coil.test.compose.AsyncImagePainter] in the given
 *  bounds defined by the width and height. 对齐方式，默认居中
 * @param contentScale Optional scale parameter used to determine the aspect ratio scaling to be
 *  used if the bounds are a different size from the intrinsic size of the [com.coil.test.compose.AsyncImagePainter].
 * @param alpha Optional opacity to be applied to the [com.coil.test.compose.AsyncImagePainter] when it is rendered
 *  onscreen. 透明度
 * @param colorFilter Optional [ColorFilter] to apply for the [com.coil.test.compose.AsyncImagePainter] when it is
 *  rendered onscreen. 颜色滤镜
 * @param filterQuality Sampling algorithm applied to a bitmap when it is scaled and drawn into the
 *  destination. 图像缩放时使用的采样质量
 */
@Composable
fun AsyncImage(
    model: Any?,
    contentDescription: String?,
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
    imageLoader = LocalImageLoader.current,
    modifier = modifier,
    placeholder = placeholder,
    error = error,
    fallback = fallback,
    onLoading = onLoading,
    onSuccess = onSuccess,
    onError = onError,
    alignment = alignment,
    contentScale = contentScale,
    alpha = alpha,
    colorFilter = colorFilter,
    filterQuality = filterQuality
)

/**
 * A composable that executes an [ImageRequest] asynchronously and renders the result.
 * 用 transform: (State) -> State 替代了 placeholder、error 等参数，用于对加载状态进行统一变换，比如可以自定义状态对应的 Painter
 * onState: 每次状态变更（包括 loading、success、error）都会回调此函数。
 *
 * @param model Either an [ImageRequest] or the [ImageRequest.data] value.
 * @param contentDescription Text used by accessibility services to describe what this image
 *  represents. This should always be provided unless this image is used for decorative purposes,
 *  and does not represent a meaningful action that a user can take.
 * @param modifier Modifier used to adjust the layout algorithm or draw decoration content.
 * @param transform A callback to transform a new [State] before it's applied to the
 *  [com.coil.test.compose.AsyncImagePainter]. Typically this is used to modify the state's [Painter].
 * @param onState Called when the state of this painter changes.
 * @param alignment Optional alignment parameter used to place the [com.coil.test.compose.AsyncImagePainter] in the given
 *  bounds defined by the width and height.
 * @param contentScale Optional scale parameter used to determine the aspect ratio scaling to be
 *  used if the bounds are a different size from the intrinsic size of the [com.coil.test.compose.AsyncImagePainter].
 * @param alpha Optional opacity to be applied to the [com.coil.test.compose.AsyncImagePainter] when it is rendered
 *  onscreen.
 * @param colorFilter Optional [ColorFilter] to apply for the [com.coil.test.compose.AsyncImagePainter] when it is
 *  rendered onscreen.
 * @param filterQuality Sampling algorithm applied to a bitmap when it is scaled and drawn into the
 *  destination.
 */
@Composable
fun AsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    transform: (State) -> State = DefaultTransform,
    onState: ((State) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
) = AsyncImage(
    model = model,
    contentDescription = contentDescription,
    imageLoader = LocalImageLoader.current,
    modifier = modifier,
    transform = transform,
    onState = onState,
    alignment = alignment,
    contentScale = contentScale,
    alpha = alpha,
    colorFilter = colorFilter,
    filterQuality = filterQuality
)
