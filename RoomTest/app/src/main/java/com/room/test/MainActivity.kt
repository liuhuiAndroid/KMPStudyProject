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
        )
            .fallbackToDestructiveMigration()
            .build()

        setContent {
            RoomTestTheme {
                val coroutineScope = rememberCoroutineScope()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = MainViewModel(bookDatabase)
                    val books by viewModel.favoriteBooks.collectAsState(initial = emptyList())

                    Column {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    withContext(Dispatchers.IO) {
                                        val id1 = bookDatabase.favoriteBookDao.insert(
                                            BookEntity(
                                                "1",
                                                "Hello World"
                                            )
                                        )
                                        val id2 = bookDatabase.favoriteBookDao.insert(
                                            BookEntity(
                                                "2",
                                                "Hello World2"
                                            )
                                        )
                                        Log.i("MainActivity", "id1: $id1, id2: $id2")
                                    }
                                }
                            },
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            Text(text = "Hello World")
                        }
                        books.forEach { book ->
                            Text(text = "Book: ${book.title}")
                        }
                    }

                }
            }
        }
    }
}
