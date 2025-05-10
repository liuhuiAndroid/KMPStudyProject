package com.mvi.test.database

import androidx.room.RoomDatabase
//import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

fun getRoomDatabase(
    builder: RoomDatabase.Builder<BookDatabase>
): BookDatabase {
    return builder
        .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)// 当数据库版本发生变化且没有提供适当的迁移策略时，直接销毁（删除）旧数据库并创建一个新的数据库。适用于开发阶段
        // 如果数据库升级涉及复杂的表结构变更，建议使用 .addMigrations(...) 而不是 .fallbackToDestructiveMigration()，以避免用户数据丢失。
//        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}