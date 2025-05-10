package com.mvi.test.screen.book

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.test.database.Book
import com.mvi.test.database.BookDatabase
import kotlinx.coroutines.launch

const val IMAGE_URL = "https://r2.erweima.ai/imgcompressed/compressed_c29235854ffdfec1257c539ecfc35783.webp"

class BookManageViewModel(
    private val selectedBookId: Int,
    private val database: BookDatabase
): ViewModel() {
    var imageField = mutableStateOf(IMAGE_URL)
    var titleField = mutableStateOf("The Art of War")
    var summaryField = mutableStateOf("Summary")
    var categoryField = mutableStateOf("Category")
    var tagsField = mutableStateOf("Tags")
    var authorField = mutableStateOf("author")

    init {
        viewModelScope.launch {
            if (selectedBookId != -1) {
                val selectedBook = database.bookDao()
                    .getBookById(selectedBookId)
                imageField.value = selectedBook.image
                titleField.value = selectedBook.title
                summaryField.value = selectedBook.summary
                categoryField.value = selectedBook.category
                tagsField.value = selectedBook.tags.joinToString()
                authorField.value = selectedBook.author
            }
        }
    }

    fun insertBook(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (
                    titleField.value.isNotEmpty() &&
                    summaryField.value.isNotEmpty() &&
                    categoryField.value.isNotEmpty() &&
                    tagsField.value.isNotEmpty() &&
                    authorField.value.isNotEmpty()
                ) {
                    database.bookDao()
                        .insertBook(
                            book = Book(
                                image = imageField.value,
                                title = titleField.value,
                                summary = summaryField.value,
                                category = categoryField.value,
                                tags = tagsField.value.split(","),
                                author = authorField.value,
                                isFavorite = false
                            ),
                        )
                    onSuccess()
                } else {
                    onError("Fields cannot be empty.")
                }
            } catch (e: Exception) {
                onError(e.toString())
            }
        }
    }

    fun updateBook(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (titleField.value.isNotEmpty() &&
                    summaryField.value.isNotEmpty() &&
                    categoryField.value.isNotEmpty() &&
                    tagsField.value.isNotEmpty() &&
                    authorField.value.isNotEmpty()
                ) {
                    database.bookDao()
                        .updateBook(
                            book = Book(
                                _id = selectedBookId,
                                image = imageField.value,
                                title = titleField.value,
                                summary = summaryField.value,
                                category = categoryField.value,
                                tags = tagsField.value.split(","),
                                author = authorField.value,
                                isFavorite = database.bookDao()
                                    .getBookById(selectedBookId).isFavorite
                            )
                        )
                    onSuccess()
                } else {
                    onError("Fields cannot be empty.")
                }
            } catch (e: Exception) {
                onError(e.toString())
            }
        }
    }
}