package com.mvi.test.screen.book

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.test.database.Book
import com.mvi.test.database.BookDatabase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val selectedBookId: Int,
    private val database: BookDatabase
): ViewModel() {
    var selectedBook: MutableState<Book?> = mutableStateOf(null)
        private set
    var isFavorite = mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            database.bookDao()
                .getBookByIdFlow(selectedBookId)
                .collectLatest {
                    selectedBook.value = it
                    isFavorite.value = it?.isFavorite ?: false
                }
        }
    }

    fun setFavoriteBook() {
        viewModelScope.launch {
            if(selectedBook.value?._id != null) {
                database.bookDao()
                    .setFavoriteBook(
                        bookId = selectedBook.value!!._id,
                        isFavorite = !isFavorite.value
                    )
            }
        }
    }

    fun deleteBook() {
        viewModelScope.launch {
            database.bookDao()
                .deleteBookById(selectedBookId)
        }
    }
}