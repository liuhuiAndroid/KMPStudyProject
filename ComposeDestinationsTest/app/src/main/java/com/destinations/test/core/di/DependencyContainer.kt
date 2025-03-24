package com.destinations.test.core.di

import androidx.lifecycle.SavedStateHandle
import com.destinations.test.MainActivity
import com.destinations.test.MainViewModel
import com.destinations.test.account.AccountViewModel
import com.destinations.test.login.data.LoginStateRepository
import com.destinations.test.tasks.data.StepsRepository
import com.destinations.test.tasks.data.TasksRepository
import com.destinations.test.tasks.presentation.details.StepDetailsViewModel
import com.destinations.test.tasks.presentation.details.StepScreenNavArgs
import com.destinations.test.tasks.presentation.details.TaskDetailsViewModel
import com.destinations.test.tasks.presentation.list.TaskListViewModel
import com.destinations.test.tasks.presentation.new.AddStepDialogNavArgs
import com.destinations.test.tasks.presentation.new.AddStepViewModel
import com.destinations.test.tasks.presentation.new.AddTaskViewModel
import com.ramcosta.composedestinations.generated.navArgs

class DependencyContainer(
    val activity: MainActivity
) {

    private val loginStateRepository: LoginStateRepository by lazy { LoginStateRepository() }

    private val tasksRepository: TasksRepository by lazy { TasksRepository(stepsRepository) }

    private val stepsRepository: StepsRepository by lazy { StepsRepository() }

    @Suppress("UNCHECKED_CAST")
    fun <T> createViewModel(modelClass: Class<T>, handle: SavedStateHandle): T {
        return when (modelClass) {
            MainViewModel::class.java -> MainViewModel(loginStateRepository)
            AccountViewModel::class.java -> AccountViewModel(loginStateRepository)
            TaskListViewModel::class.java -> TaskListViewModel(tasksRepository, stepsRepository)
            AddTaskViewModel::class.java -> AddTaskViewModel(tasksRepository)
            AddStepViewModel::class.java -> AddStepViewModel(
                handle.navArgs<AddStepDialogNavArgs>().taskId,
                stepsRepository
            )
            TaskDetailsViewModel::class.java -> TaskDetailsViewModel(
                handle,
                tasksRepository,
                stepsRepository
            )
            StepDetailsViewModel::class.java -> StepDetailsViewModel(
                handle.navArgs<StepScreenNavArgs>().stepId,
                tasksRepository,
                stepsRepository
            )
            else -> throw RuntimeException("Unknown view model $modelClass")
        } as T
    }
}