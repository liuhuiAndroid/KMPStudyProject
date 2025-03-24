package com.destinations.test.tasks.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.destinations.test.tasks.data.StepsRepository
import com.destinations.test.tasks.data.TasksRepository
import com.destinations.test.tasks.domain.Step
import com.destinations.test.tasks.domain.Task
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StepDetailsViewModel(
    private val stepId: Int,
    private val tasksRepository: TasksRepository,
    private val stepsRepository: StepsRepository
): ViewModel() {

    val step: StateFlow<Step?> = stepsRepository.stepForId(stepId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    @OptIn(FlowPreview::class)
    val task: StateFlow<Task?> = stepsRepository.stepForId(stepId)
        .flatMapConcat {
            tasksRepository.taskById(it.taskId)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun onStepCheckedChange(checked: Boolean) {
        val value = step.value ?: return
        viewModelScope.launch {
            stepsRepository.updateStep(value.copy(completed = checked))
        }
    }
}