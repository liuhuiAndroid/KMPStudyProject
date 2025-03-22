package com.room.test

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.room.test.ui.theme.RoomTestTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * https://developer.android.com/jetpack/androidx/releases/room?hl=zh-cn
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContext = this@MainActivity.applicationContext
        val bookDatabase = Room.databaseBuilder(
            context = appContext,
            FavoriteBookDatabase::class.java,
            name = FavoriteBookDatabase.DB_NAME // 跨平台项目推荐使用绝对路径
        ).fallbackToDestructiveMigration() // 当数据库版本发生变化且没有提供适当的迁移策略时，直接销毁（删除）旧数据库并创建一个新的数据库。适用于开发阶段
            // 如果数据库升级涉及复杂的表结构变更，建议使用 .addMigrations(...) 而不是 .fallbackToDestructiveMigration()，以避免用户数据丢失。
            .build()

        setContent {
            RoomTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        val coroutineScope = rememberCoroutineScope()
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    withContext(Dispatchers.IO) {
                                        val id1 = bookDatabase.favoriteBookDao.upsert(
                                            BookEntity(
                                                "1", "Hello World"
                                            )
                                        )
                                        val id2 = bookDatabase.favoriteBookDao.upsert(
                                            BookEntity(
                                                "2", "Hello World2"
                                            )
                                        )
                                        Log.i("MainActivity", "id1: $id1, id2: $id2")
                                    }
                                }
                            }, modifier = Modifier.padding(innerPadding)
                        ) {
                            Text(text = "Hello World")
                        }
                        val viewModel = MainViewModel(bookDatabase)
                        val books by viewModel.favoriteBooks.collectAsState(initial = emptyList())
                        books.forEach { book ->
                            Text(text = "Book: ${book.title}")
                        }
                    }

                }
            }
        }
    }
}
