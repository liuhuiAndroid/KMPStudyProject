package com.room.test

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [BookEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    StringListTypeConverter::class
)
abstract class FavoriteBookDatabase : RoomDatabase() {
    abstract val favoriteBookDao: FavoriteBookDao

    companion object {
        const val DB_NAME = "book.db"
    }
}