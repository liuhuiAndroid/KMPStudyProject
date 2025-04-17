package com.coil.test.compose

import android.os.SystemClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.painter.Painter
import coil.decode.DecodeUtils
import coil.size.Scale
import kotlin.math.max

/**
 * 渐变动画
 * Return a [CrossfadePainter] for the given [key].
 * @param key 触发重新创建的标志
 */
@Composable
internal fun rememberCrossfadePainter(
    key: Any,
    start: Painter?,
    end: Painter?,
    scale: Scale,
    durationMillis: Int,
    fadeStart: Boolean,
): Painter = remember(key) { CrossfadePainter(start, end, scale, durationMillis, fadeStart) }

/**
 * A [Painter] that crossfades from [start] to [end].
 *
 * NOTE: The animation can only be executed once as the [start] drawable is
 * dereferenced at the end of the transition.
 * @param start 起始图像（可能是旧的图片）
 * @param end 目标图像（新的加载完成的图）
 * @param scale 缩放模式（例如 FILL, FIT）
 * @param durationMillis 渐变动画持续时间
 * @param fadeStart 是否对起始图像做渐隐（有时只渐入 end）
 */
@Stable
private class CrossfadePainter(
    private var start: Painter?,
    private val end: Painter?,
    private val scale: Scale,
    private val durationMillis: Int,
    private val fadeStart: Boolean,
) : Painter() {

    // 触发重新绘制的手段（mutableStateOf 会让 Compose 重组这个 Painter）
    private var invalidateTick by mutableIntStateOf(0)

    // 第一次绘制时记录系统时间，用来计算动画进度
    private var startTimeMillis = -1L

    // 是否动画已完成
    private var isDone = false

    private var maxAlpha: Float by mutableFloatStateOf(1f)
    private var colorFilter: ColorFilter? by mutableStateOf(null)

    override val intrinsicSize get() = computeIntrinsicSize()

    /**
     * 核心绘制逻辑
     */
    override fun DrawScope.onDraw() {
        if (isDone) {
            drawPainter(end, maxAlpha)
            return
        }

        // Initialize startTimeMillis the first time we're drawn.
        val uptimeMillis = SystemClock.uptimeMillis()
        if (startTimeMillis == -1L) {
            startTimeMillis = uptimeMillis
        }

        // 每一帧都重新计算动画百分比
        val percent = (uptimeMillis - startTimeMillis) / durationMillis.toFloat()
        val endAlpha = percent.coerceIn(0f, 1f) * maxAlpha
        val startAlpha = if (fadeStart) maxAlpha - endAlpha else maxAlpha
        isDone = percent >= 1.0

        // 动态调整 start 和 end 的透明度
        drawPainter(start, startAlpha)
        drawPainter(end, endAlpha)

        if (isDone) {
            start = null // 回收 start，避免内存泄漏
        } else {
            // Increment this value to force the painter to be redrawn.
            invalidateTick++ // 利用状态变化触发 Compose 重新绘制
        }
    }

    /**
     * 允许外部设置透明度（比如 Image(alpha = 0.5f) 会走这里）
     */
    override fun applyAlpha(alpha: Float): Boolean {
        this.maxAlpha = alpha
        return true
    }

    /**
     * 允许设置色彩变换（比如黑白滤镜等）
     */
    override fun applyColorFilter(colorFilter: ColorFilter?): Boolean {
        this.colorFilter = colorFilter
        return true
    }

    /**
     * Intrinsic Size 尺寸计算
     * 取 start 和 end 中最大的尺寸作为本 Painter 的固有尺寸（Intrinsic Size）
     * 如果不确定，返回 Size.Unspecified
     */
    private fun computeIntrinsicSize(): Size {
        val startSize = start?.intrinsicSize ?: Size.Zero
        val endSize = end?.intrinsicSize ?: Size.Zero

        return if (startSize.isSpecified && endSize.isSpecified) {
            Size(
                width = max(startSize.width, endSize.width),
                height = max(startSize.height, endSize.height),
            )
        } else {
            Size.Unspecified
        }
    }

    private fun DrawScope.drawPainter(painter: Painter?, alpha: Float) {
        // 如果透明度为 0 或 painter 为 null，则跳过
        if (painter == null || alpha <= 0) return

        with(painter) {
            val size = size
            // 计算该 painter 的目标绘制尺寸（用缩放策略）
            val drawSize = computeDrawSize(intrinsicSize, size)

            if (size.isUnspecified || size.isEmpty()) {
                // 调用 painter 的 draw(...) 方法传入 alpha 和 colorFilter
                draw(drawSize, alpha, colorFilter)
            } else {
                // 如果目标尺寸小于实际 Canvas，使用 inset 将图像居中
                inset(
                    horizontal = (size.width - drawSize.width) / 2,
                    vertical = (size.height - drawSize.height) / 2
                ) { draw(drawSize, alpha, colorFilter) }
            }
        }
    }

    /**
     * Scale the src size into the dst size preserving aspect ratio.
     *
     * 计算缩放后的目标绘制尺寸，保证图像绘制时不会变形
     */
    private fun computeDrawSize(srcSize: Size, dstSize: Size): Size {
        if (srcSize.isUnspecified || srcSize.isEmpty()) return dstSize
        if (dstSize.isUnspecified || dstSize.isEmpty()) return dstSize

        val srcWidth = srcSize.width
        val srcHeight = srcSize.height
        // 用 DecodeUtils.computeSizeMultiplier(...) 计算缩放系数，保持宽高比，兼顾 Scale.FIT / FILL 模式。
        val multiplier = DecodeUtils.computeSizeMultiplier(
            srcWidth = srcWidth,
            srcHeight = srcHeight,
            dstWidth = dstSize.width,
            dstHeight = dstSize.height,
            scale = scale
        )
        return Size(
            width = multiplier * srcWidth,
            height = multiplier * srcHeight
        )
    }
}
