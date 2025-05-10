package com.mvi.test.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.mvi.test.database.dao.BookDao
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

@Database(
    entities = [Book::class], version = 1, exportSchema = true
)
//@ConstructedBy(BookDatabaseConstructor::class)
@TypeConverters(BookTypeConverter::class)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}

//object BookDatabaseConstructor : RoomDatabaseConstructor<BookDatabase> {
//    override fun initialize(): BookDatabase = getRoomDatabase(getDatabaseBuilder())
//
//}

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