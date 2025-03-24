package com.destinations.test.tasks.presentation.list

import com.destinations.test.tasks.domain.Step
import com.destinations.test.tasks.domain.Task

data class TaskUiItem(
    val task: Task,
    val steps: List<Step>
)