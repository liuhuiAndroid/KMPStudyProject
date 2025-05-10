package com.mvi.test.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<BookDatabase> {
    val dbFile = context.getDatabasePath(BookDatabase.DB_NAME)
    return Room.databaseBuilder(
        context = context,
        name = dbFile.absolutePath
    )
}