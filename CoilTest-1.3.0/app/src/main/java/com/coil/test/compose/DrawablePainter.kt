package com.coil.test.compose

import android.graphics.drawable.Animatable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asAndroidColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.withSave
import androidx.compose.ui.unit.LayoutDirection
import kotlin.LazyThreadSafetyMode.NONE
import kotlin.math.roundToInt

/**
 * Convert this [Drawable] into a [Painter] using Compose primitives if possible.
 * 根据传入的 Drawable 类型返回对应的 Painter 实现
 *
 * mutate() 是为了防止与其他组件共享状态的副作用
 */
internal fun Drawable.toPainter(): Painter {
    return when (this) {
        is BitmapDrawable -> BitmapPainter(bitmap.asImageBitmap())
        is ColorDrawable -> ColorPainter(Color(color))
        else -> DrawablePainter(mutate())
    }
}

/**
 * A [Painter] which draws an Android [Drawable] and supports [Animatable] drawables.
 *
 * Instances should be remembered to be able to start and stop [Animatable] animations.
 *
 * 这是一个自定义的 Painter，专门用来绘制 Android 原生的 Drawable
 * 实现了 RememberObserver，使其能响应 Compose 生命周期（进入组合、离开组合）
 */
@Stable
private class DrawablePainter(
    private val drawable: Drawable
) : Painter(), RememberObserver {

    // 用 invalidateTick 来触发 Compose 的重绘机制（使用 mutableStateOf 实现）
    private var invalidateTick by mutableIntStateOf(0)

    /**
     * 用于接收 drawable 的重绘和调度请求
     */
    private val callback = object : Drawable.Callback {
        override fun invalidateDrawable(who: Drawable) {
            // Update the tick so that we get re-drawn.
            invalidateTick++
        }

        override fun scheduleDrawable(who: Drawable, what: Runnable, time: Long) {
            MAIN_HANDLER.postAtTime(what, time)
        }

        override fun unscheduleDrawable(who: Drawable, what: Runnable) {
            MAIN_HANDLER.removeCallbacks(what)
        }
    }

    init {
        // Update the drawable's bounds to match the intrinsic size.
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    }

    override val intrinsicSize: Size
        get() = Size(
            width = drawable.intrinsicWidth.toFloat(),
            height = drawable.intrinsicHeight.toFloat()
        )

    override fun DrawScope.onDraw() = drawIntoCanvas { canvas ->
        // Reading this ensures that we invalidate when invalidateDrawable() is called.
        invalidateTick // 读取触发 recomposition

        // Update the drawable's bounds.
        drawable.setBounds(0, 0, size.width.roundToInt(), size.height.roundToInt())

        canvas.withSave {
            drawable.draw(canvas.nativeCanvas)
        }
    }

    override fun onRemembered() {
        // 设置 drawable 的 callback（这样 drawable 会通知我们更新）
        drawable.callback = callback
        drawable.setVisible(true, true)
        // 使其可见并自动播放动画（如果是 Animatable）
        if (drawable is Animatable) drawable.start()
    }

    /**
     * 停止动画并移除 callback，释放资源
     */
    override fun onForgotten() {
        if (drawable is Animatable) drawable.stop()
        drawable.setVisible(false, false)
        drawable.callback = null
    }

    override fun onAbandoned() = onForgotten()

    override fun applyAlpha(alpha: Float): Boolean {
        drawable.alpha = (alpha * 255).roundToInt().coerceIn(0, 255)
        return true
    }

    /**
     * Compose 的 ColorFilter → 转换为 Android 原生的 ColorFilter
     */
    override fun applyColorFilter(colorFilter: ColorFilter?): Boolean {
        drawable.colorFilter = colorFilter?.asAndroidColorFilter()
        return true
    }

    /**
     * 支持 LTR 和 RTL 布局方向（Android 6.0 以上）
     */
    override fun applyLayoutDirection(layoutDirection: LayoutDirection): Boolean {
        if (SDK_INT >= 23) {
            return drawable.setLayoutDirection(
                when (layoutDirection) {
                    LayoutDirection.Ltr -> View.LAYOUT_DIRECTION_LTR
                    LayoutDirection.Rtl -> View.LAYOUT_DIRECTION_RTL
                }
            )
        }
        return false
    }
}

// 用来处理调度回调，比如动画帧的刷新，确保运行在主线程。
private val MAIN_HANDLER by lazy(NONE) { Handler(Looper.getMainLooper()) }
