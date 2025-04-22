package com.coil.test.compose

import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import coil.size.Size
import coil.size.SizeResolver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapNotNull

/**
 * A [SizeResolver] that computes the size from the constraints passed during the layout phase.
 * 根据布局阶段传入的约束（constraints）来计算尺寸
 * 用 ConstraintsSizeResolver：等到布局阶段拿到 Constraints，然后把它转换成 coil.size.Size，用来加载合适尺寸的图片。
 *
 * SizeResolver：Coil 用来获取目标显示尺寸（用于请求正确尺寸的图片）
 * LayoutModifier：Compose 用来自定义布局测量逻辑（关键点在这里获取约束）
 */
internal class ConstraintsSizeResolver : SizeResolver, LayoutModifier {

    // StateFlow，用来保存最新的 Constraints（Compose 的测量约束）
    private val currentConstraints = MutableStateFlow(ZeroConstraints)

    override suspend fun size(): Size {
        return currentConstraints
            .mapNotNull(Constraints::toSizeOrNull)
            .first()
    }

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints,
    ): MeasureResult {
        // Cache the current constraints.
        currentConstraints.value = constraints

        // Measure and layout the content.
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    }

    /**
     * 手动设置约束的方法
     */
    fun setConstraints(constraints: Constraints) {
        currentConstraints.value = constraints
    }
}
