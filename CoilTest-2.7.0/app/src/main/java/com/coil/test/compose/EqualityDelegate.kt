package com.coil.test.compose

import androidx.compose.runtime.Stable
import coil.request.ImageRequest

/**
 * Determines equality between two values or a value's hash code.
 *
 * Coil 使用 ImageRequest 来标识一张图片是否已经加载过或在缓存中：
 * - 如果两个请求 equals()，就会直接从缓存中取图，而不是重新下载。
 * - 所以必须保证请求参数完全一致，equals() 才返回 true。
 */
@Stable
interface EqualityDelegate {
    /**
     * 作用：判断两个对象是否“内容相等”
     * 它默认行为是判断引用地址是否一样，除非你重写它
     *
     * == 实际调用的是 a.equals(b)
     * === 是比较内存地址（是否是同一个对象）
     */
    fun equals(self: Any?, other: Any?): Boolean

    /**
     * 返回一个整数，用于哈希表等数据结构。=> 提高查找效率 => 先用 hashCode() 定位，再用 equals() 精确判断
     * 如果你把对象当作 HashMap 的 key，就必须合理实现 hashCode()。
     *
     * 关键原则：如果两个对象通过 equals() 相等，那么它们的 hashCode() 也必须相等。
     * 否则，像 HashSet、HashMap、remember（Compose 的缓存）这些就会出错或逻辑混乱。
     */
    fun hashCode(self: Any?): Int
}

/**
 * The default [EqualityDelegate] used to determine equality and the hash code for the `model`
 * argument for [rememberAsyncImagePainter], [AsyncImage], and [SubcomposeAsyncImage].
 */
val DefaultModelEqualityDelegate = object : EqualityDelegate {

    override fun equals(self: Any?, other: Any?): Boolean {
        if (self === other) { // 引用相同，直接返回 true
            return true
        }

        if (self !is ImageRequest || other !is ImageRequest) { // 如果不是 ImageRequest 类型，就用普通的 == 逻辑比较（即调用 equals()）。
            return self == other
        }

        // 如果是 ImageRequest，逐一比较其 关键字段。
        return self.context == other.context &&
                self.data == other.data &&
                self.placeholderMemoryCacheKey == other.placeholderMemoryCacheKey &&
                self.memoryCacheKey == other.memoryCacheKey &&
                self.diskCacheKey == other.diskCacheKey &&
                self.bitmapConfig == other.bitmapConfig &&
                self.colorSpace == other.colorSpace &&
                self.transformations == other.transformations &&
                self.headers == other.headers &&
                self.allowConversionToBitmap == other.allowConversionToBitmap &&
                self.allowHardware == other.allowHardware &&
                self.allowRgb565 == other.allowRgb565 &&
                self.premultipliedAlpha == other.premultipliedAlpha &&
                self.memoryCachePolicy == other.memoryCachePolicy &&
                self.diskCachePolicy == other.diskCachePolicy &&
                self.networkCachePolicy == other.networkCachePolicy &&
                self.sizeResolver == other.sizeResolver &&
                self.scale == other.scale &&
                self.precision == other.precision &&
                self.parameters == other.parameters
    }

    override fun hashCode(self: Any?): Int {
        if (self !is ImageRequest) { // 如果不是 ImageRequest，直接调用默认的 hashCode()。
            return self.hashCode()
        }

        // 如果是 ImageRequest，就根据所有关键字段生成哈希值。
        // 使用经典的乘以 31 的 hash 链式计算方式（Kotlin 常用写法）。
        // 保证 equals() 相等的两个对象，其 hashCode() 也一致（哈希表要求）。
        var result = self.context.hashCode()
        result = 31 * result + self.data.hashCode()
        result = 31 * result + self.placeholderMemoryCacheKey.hashCode()
        result = 31 * result + self.memoryCacheKey.hashCode()
        result = 31 * result + self.diskCacheKey.hashCode()
        result = 31 * result + self.bitmapConfig.hashCode()
        result = 31 * result + self.colorSpace.hashCode()
        result = 31 * result + self.transformations.hashCode()
        result = 31 * result + self.headers.hashCode()
        result = 31 * result + self.allowConversionToBitmap.hashCode()
        result = 31 * result + self.allowHardware.hashCode()
        result = 31 * result + self.allowRgb565.hashCode()
        result = 31 * result + self.premultipliedAlpha.hashCode()
        result = 31 * result + self.memoryCachePolicy.hashCode()
        result = 31 * result + self.diskCachePolicy.hashCode()
        result = 31 * result + self.networkCachePolicy.hashCode()
        result = 31 * result + self.sizeResolver.hashCode()
        result = 31 * result + self.scale.hashCode()
        result = 31 * result + self.precision.hashCode()
        result = 31 * result + self.parameters.hashCode()
        return result
    }
}
