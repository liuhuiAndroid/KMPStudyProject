@file:Suppress("DEPRECATION")

package com.coil.test.singleton

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.coil.test.compose.AsyncImagePainter.Companion.DefaultTransform
import com.coil.test.compose.AsyncImagePainter.State
import coil.request.ImageRequest
import com.coil.test.compose.DefaultModelEqualityDelegate
import com.coil.test.compose.EqualityDelegate
import com.coil.test.compose.rememberAsyncImagePainter

/**
 * Return an [com.coil.test.compose.AsyncImagePainter] that executes an [ImageRequest] asynchronously and renders the result.
 *
 * ** This is a lower-level API than [com.coil.test.compose.AsyncImage] and may not work as expected in all situations. **
 *
 * - [com.coil.test.compose.AsyncImagePainter] will not finish loading if [com.coil.test.compose.AsyncImagePainter.onDraw] is not called.
 *   This can occur if a composable has an unbounded (i.e. [Constraints.Infinity]) width/height
 *   constraint. For example, to use [com.coil.test.compose.AsyncImagePainter] with [LazyRow] or [LazyColumn], you must
 *   set a bounded width or height respectively using `Modifier.width` or `Modifier.height`.
 * - [com.coil.test.compose.AsyncImagePainter.state] will not transition to [State.Success] synchronously during the
 *   composition phase. Use [com.coil.test.compose.SubcomposeAsyncImage] or set a custom [ImageRequest.Builder.size] value
 *   (e.g. `size(Size.ORIGINAL)`) if you need this.
 *
 * @param model Either an [ImageRequest] or the [ImageRequest.data] value.
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
 * @param modelEqualityDelegate Determines the equality of [model]. This controls whether this
 *  composable is redrawn and a new image request is launched when the outer composable recomposes.
 */
@Composable
@NonRestartableComposable
fun rememberAsyncImagePainter(
    model: Any?,
    placeholder: Painter? = null,
    error: Painter? = null,
    fallback: Painter? = error,
    onLoading: ((State.Loading) -> Unit)? = null,
    onSuccess: ((State.Success) -> Unit)? = null,
    onError: ((State.Error) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Fit,
    filterQuality: FilterQuality = DefaultFilterQuality,
    modelEqualityDelegate: EqualityDelegate = DefaultModelEqualityDelegate,
) = rememberAsyncImagePainter(
    model = model,
    imageLoader = LocalImageLoader.current,
    placeholder = placeholder,
    error = error,
    fallback = fallback,
    onLoading = onLoading,
    onSuccess = onSuccess,
    onError = onError,
    contentScale = contentScale,
    filterQuality = filterQuality,
    modelEqualityDelegate = modelEqualityDelegate,
)

@Deprecated(message = "Kept for binary compatibility.", level = DeprecationLevel.HIDDEN)
@Composable
@NonRestartableComposable
fun rememberAsyncImagePainter(
    model: Any?,
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
    imageLoader = LocalImageLoader.current,
    placeholder = placeholder,
    error = error,
    fallback = fallback,
    onLoading = onLoading,
    onSuccess = onSuccess,
    onError = onError,
    contentScale = contentScale,
    filterQuality = filterQuality,
)

/**
 * Return an [com.coil.test.compose.AsyncImagePainter] that executes an [ImageRequest] asynchronously and renders the result.
 *
 * ** This is a lower-level API than [com.coil.test.compose.AsyncImage] and may not work as expected in all situations. **
 *
 * - [com.coil.test.compose.AsyncImagePainter] will not finish loading if [com.coil.test.compose.AsyncImagePainter.onDraw] is not called.
 *   This can occur if a composable has an unbounded (i.e. [Constraints.Infinity]) width/height
 *   constraint. For example, to use [com.coil.test.compose.AsyncImagePainter] with [LazyRow] or [LazyColumn], you must
 *   set a bounded width or height respectively using `Modifier.width` or `Modifier.height`.
 * - [com.coil.test.compose.AsyncImagePainter.state] will not transition to [State.Success] synchronously during the
 *   composition phase. Use [com.coil.test.compose.SubcomposeAsyncImage] or set a custom [ImageRequest.Builder.size] value
 *   (e.g. `size(Size.ORIGINAL)`) if you need this.
 *
 * @param model Either an [ImageRequest] or the [ImageRequest.data] value.
 * @param transform A callback to transform a new [State] before it's applied to the
 *  [com.coil.test.compose.AsyncImagePainter]. Typically this is used to overwrite the state's [Painter].
 * @param onState Called when the state of this painter changes.
 * @param contentScale Used to determine the aspect ratio scaling to be used if the canvas bounds
 *  are a different size from the intrinsic size of the image loaded by [model]. This should be set
 *  to the same value that's passed to [Image].
 * @param filterQuality Sampling algorithm applied to a bitmap when it is scaled and drawn into the
 *  destination.
 * @param modelEqualityDelegate Determines the equality of [model]. This controls whether this
 *  composable is redrawn and a new image request is launched when the outer composable recomposes.
 */
@Composable
@NonRestartableComposable
fun rememberAsyncImagePainter(
    model: Any?,
    transform: (State) -> State = DefaultTransform,
    onState: ((State) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Fit,
    filterQuality: FilterQuality = DefaultFilterQuality,
    modelEqualityDelegate: EqualityDelegate = DefaultModelEqualityDelegate,
) = rememberAsyncImagePainter(
    model = model,
    imageLoader = LocalImageLoader.current,
    transform = transform,
    onState = onState,
    contentScale = contentScale,
    filterQuality = filterQuality,
    modelEqualityDelegate = modelEqualityDelegate,
)

@Deprecated(message = "Kept for binary compatibility.", level = DeprecationLevel.HIDDEN)
@Composable
@NonRestartableComposable
fun rememberAsyncImagePainter(
    model: Any?,
    transform: (State) -> State = DefaultTransform,
    onState: ((State) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Fit,
    filterQuality: FilterQuality = DefaultFilterQuality,
) = rememberAsyncImagePainter(
    model = model,
    imageLoader = LocalImageLoader.current,
    transform = transform,
    onState = onState,
    contentScale = contentScale,
    filterQuality = filterQuality,
)
