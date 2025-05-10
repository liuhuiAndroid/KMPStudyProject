package com.mvi.test.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mvi.test.database.dao.BookDao

@Database(
    entities = [Book::class], version = 1, exportSchema = true
)
@TypeConverters(BookTypeConverter::class)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        const val DB_NAME = "book.db"
    }
}
