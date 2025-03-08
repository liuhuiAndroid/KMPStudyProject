package com.plcoding.noteappkmm.android.di

import android.app.Application
import com.plcoding.noteappkmm.data.local.DatabaseDriverFactory
import com.plcoding.noteappkmm.data.note.SqlDelightNoteDataSource
import com.plcoding.noteappkmm.database.NoteDatabase
import com.plcoding.noteappkmm.domain.note.NoteDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module // Hilt 模块
@InstallIn(SingletonComponent::class)   // 应用级别的单例
object AppModule {

    @Provides   // 依赖提供方法
    @Singleton  // 只会有一个实例
    fun provideSqlDriver(application: Application): SqlDriver {
        return DatabaseDriverFactory(application).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(driver: SqlDriver): NoteDataSource {
        return SqlDelightNoteDataSource(NoteDatabase(driver))
    }
}