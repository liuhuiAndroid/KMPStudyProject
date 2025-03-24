package com.destinations.test.tasks.domain

data class Task(
    val title: String,
    val description: String,
    val completed: Boolean,
    val id: Int = 0,
)
