package com.mvi.test.database

import androidx.room.TypeConverter
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

class BookTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<String> = Json.decodeFromString(
        ListSerializer(String.serializer()), value
    )

    @TypeConverter
    fun fromList(list: List<String>): String = Json.encodeToString(
        ListSerializer(String.serializer()), list
    )
}