package com.room.test

import androidx.lifecycle.ViewModel

class MainViewModel(bookDatabase: FavoriteBookDatabase) : ViewModel() {
    val favoriteBooks = bookDatabase.favoriteBookDao.getFavoriteBooks()
}