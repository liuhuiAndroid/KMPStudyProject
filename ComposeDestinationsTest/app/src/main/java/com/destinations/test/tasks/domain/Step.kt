package com.destinations.test.tasks.domain

data class Step(
    val taskId: Int,
    val title: String,
    val completed: Boolean,
    val id: Int = 0,
)
