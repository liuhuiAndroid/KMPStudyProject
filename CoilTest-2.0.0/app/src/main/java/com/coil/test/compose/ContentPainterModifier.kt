package com.coil.test.compose

import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.times
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * A custom [paint] modifier used by [Content].
 * 图片绘制的核心实现 => 在指定区域里以合适的方式绘制一张图片
 * LayoutModifier => 控制布局测量逻辑
 * DrawModifier => 控制绘制逻辑
 * InspectorValueInfo => 方便 Compose 调试时显示属性名
 */
internal data class ContentPainterModifier(
    private val painter: Painter,
    private val alignment: Alignment,
    private val contentScale: ContentScale,
    private val alpha: Float,
    private val colorFilter: ColorFilter?,
) : LayoutModifier, DrawModifier, InspectorValueInfo(
    debugInspectorInfo {
        name = "content"
        properties["painter"] = painter
        properties["alignment"] = alignment
        properties["contentScale"] = contentScale
        properties["alpha"] = alpha
        properties["colorFilter"] = colorFilter
    }
) {

    /**
     * 控制布局大小
     */
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        // modifyConstraints() 会根据图片本身的 intrinsicSize（固有大小）、缩放方式、父布局限制等，计算出合适的目标大小
        val placeable = measurable.measure(modifyConstraints(constraints))
        return layout(placeable.width, placeable.height) {
            // 把图片放到坐标原点
            placeable.placeRelative(0, 0)
        }
    }

    /**
     * 如果 painter.intrinsicSize 是指定过的，就以它为基础计算缩放后的大小
     * 否则使用子组件原始的 intrinsic 尺寸
     */
    override fun IntrinsicMeasureScope.minIntrinsicWidth(
        measurable: IntrinsicMeasurable,
        height: Int
    ): Int {
        return if (painter.intrinsicSize.isSpecified) {
            val constraints = Constraints(maxHeight = height)
            val layoutWidth = measurable.minIntrinsicWidth(modifyConstraints(constraints).maxHeight)
            val scaledSize = calculateScaledSize(Size(layoutWidth.toFloat(), height.toFloat()))
            max(scaledSize.width.roundToInt(), layoutWidth)
        } else {
            measurable.minIntrinsicWidth(height)
        }
    }

    /**
     * 如果 painter.intrinsicSize 是指定过的，就以它为基础计算缩放后的大小
     * 否则使用子组件原始的 intrinsic 尺寸
     */
    override fun IntrinsicMeasureScope.maxIntrinsicWidth(
        measurable: IntrinsicMeasurable,
        height: Int
    ): Int {
        return if (painter.intrinsicSize.isSpecified) {
            val constraints = Constraints(maxHeight = height)
            val layoutWidth = measurable.maxIntrinsicWidth(modifyConstraints(constraints).maxHeight)
            val scaledSize = calculateScaledSize(Size(layoutWidth.toFloat(), height.toFloat()))
            max(scaledSize.width.roundToInt(), layoutWidth)
        } else {
            measurable.maxIntrinsicWidth(height)
        }
    }

    /**
     * 如果 painter.intrinsicSize 是指定过的，就以它为基础计算缩放后的大小
     * 否则使用子组件原始的 intrinsic 尺寸
     */
    override fun IntrinsicMeasureScope.minIntrinsicHeight(
        measurable: IntrinsicMeasurable,
        width: Int
    ): Int {
        return if (painter.intrinsicSize.isSpecified) {
            val constraints = Constraints(maxWidth = width)
            val layoutHeight = measurable.minIntrinsicHeight(modifyConstraints(constraints).maxWidth)
            val scaledSize = calculateScaledSize(Size(width.toFloat(), layoutHeight.toFloat()))
            max(scaledSize.height.roundToInt(), layoutHeight)
        } else {
            measurable.minIntrinsicHeight(width)
        }
    }

    /**
     * 如果 painter.intrinsicSize 是指定过的，就以它为基础计算缩放后的大小
     * 否则使用子组件原始的 intrinsic 尺寸
     */
    override fun IntrinsicMeasureScope.maxIntrinsicHeight(
        measurable: IntrinsicMeasurable,
        width: Int
    ): Int {
        return if (painter.intrinsicSize.isSpecified) {
            val constraints = Constraints(maxWidth = width)
            val layoutHeight = measurable.maxIntrinsicHeight(modifyConstraints(constraints).maxWidth)
            val scaledSize = calculateScaledSize(Size(width.toFloat(), layoutHeight.toFloat()))
            max(scaledSize.height.roundToInt(), layoutHeight)
        } else {
            measurable.maxIntrinsicHeight(width)
        }
    }

    /**
     * 根据 contentScale 和 painter 的原始尺寸（intrinsicSize）来计算出最终绘制时应该缩放到的尺寸
     */
    private fun calculateScaledSize(dstSize: Size): Size {
        if (dstSize.isEmpty()) return Size.Zero

        val intrinsicSize = painter.intrinsicSize
        if (intrinsicSize.isUnspecified) return dstSize

        val srcSize = Size(
            width = intrinsicSize.width.takeOrElse { dstSize.width },
            height = intrinsicSize.height.takeOrElse { dstSize.height }
        )
        return srcSize * contentScale.computeScaleFactor(srcSize, dstSize)
    }

    /**
     * 重要方法
     * 根据 painter 的 intrinsic size 和 contentScale，对外部传入的 constraints 做适当的修改，让布局系统分配合适的大小
     */
    private fun modifyConstraints(constraints: Constraints): Constraints {
        // The constraints are a fixed pixel value that can't be modified.
        val hasFixedWidth = constraints.hasFixedWidth
        val hasFixedHeight = constraints.hasFixedHeight
        // 如果宽高都固定，直接返回
        if (hasFixedWidth && hasFixedHeight) {
            return constraints
        }

        // Fill the available space if the painter has no intrinsic size.
        val hasBoundedSize = constraints.hasBoundedWidth && constraints.hasBoundedHeight
        val intrinsicSize = painter.intrinsicSize
        // 如果 intrinsic size 未指定
        // - 且父布局是“有限边界”：就填满
        // - 否则保持原 constraints
        if (intrinsicSize.isUnspecified) {
            if (hasBoundedSize) {
                return constraints.copy(
                    minWidth = constraints.maxWidth,
                    minHeight = constraints.maxHeight
                )
            } else {
                return constraints
            }
        }

        // Changed from `PainterModifier`:
        // Use the maximum space as the destination size if the constraints are bounded and at
        // least one dimension is a fixed pixel value. Else, use the intrinsic size of the painter.
        // - 如果父容器尺寸受限（如 Box 设置了宽高），并且至少一个维度固定，就用最大空间
        // - 否则使用图片自己的固有尺寸
        val dstWidth: Float
        val dstHeight: Float
        if (hasBoundedSize && (hasFixedWidth || hasFixedHeight)) {
            dstWidth = constraints.maxWidth.toFloat()
            dstHeight = constraints.maxHeight.toFloat()
        } else {
            val (intrinsicWidth, intrinsicHeight) = intrinsicSize
            dstWidth = when {
                intrinsicWidth.isFinite() -> constraints.constrainWidth(intrinsicWidth)
                else -> constraints.minWidth.toFloat()
            }
            dstHeight = when {
                intrinsicHeight.isFinite() -> constraints.constrainHeight(intrinsicHeight)
                else -> constraints.minHeight.toFloat()
            }
        }

        // Scale the source dimensions into the destination dimensions and update the constraints.
        // 用 contentScale 缩放一下，得出最终绘图区域
        val (scaledWidth, scaledHeight) = calculateScaledSize(Size(dstWidth, dstHeight))
        return constraints.copy(
            minWidth = constraints.constrainWidth(scaledWidth.roundToInt()),
            minHeight = constraints.constrainHeight(scaledHeight.roundToInt())
        )
    }

    /**
     * 实际绘图逻辑
     */
    override fun ContentDrawScope.draw() {
        // 计算缩放后的绘图尺寸
        val scaledSize = calculateScaledSize(size)
        // 根据 alignment 算出偏移量
        val (dx, dy) = alignment.align(
            size = scaledSize.toIntSize(),
            space = size.toIntSize(),
            layoutDirection = layoutDirection
        )

        // Draw the painter.
        // 平移坐标系 + 绘图
        translate(dx.toFloat(), dy.toFloat()) {
            with(painter) {
                draw(scaledSize, alpha, colorFilter)
            }
        }

        // Draw any child content on top of the painter.
        // 绘制子内容（如果有）=> 支持内容叠加（比如图片上画文字、渐变等）
        drawContent()
    }
}
