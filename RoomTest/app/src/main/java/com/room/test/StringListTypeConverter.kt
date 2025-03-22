package com.room.test

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

/**
 *  用于处理 Room 默认不支持的数据类型（如 List<String>、自定义对象等）
 */
object StringListTypeConverter {
    @TypeConverter
    fun fromList(list: List<String>): String = Json.encodeToString(list)

    @TypeConverter
    fun toList(value: String): List<String> = Json.decodeFromString(value)
}