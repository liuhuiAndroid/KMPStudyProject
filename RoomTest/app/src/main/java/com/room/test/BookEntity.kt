package com.room.test

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
)
