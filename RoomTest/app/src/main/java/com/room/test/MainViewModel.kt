package com.room.test

import androidx.lifecycle.ViewModel

class MainViewModel(private val bookDatabase: FavoriteBookDatabase) : ViewModel() {
    val favoriteBooks = bookDatabase.favoriteBookDao.getFavoriteBooks()
}