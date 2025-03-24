package com.destinations.test.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.generated.destinations.AccountScreenDestination
import com.ramcosta.composedestinations.generated.destinations.AddStepDialogDestination
import com.ramcosta.composedestinations.generated.destinations.AddTaskDialogDestination
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.StepScreenDestination
import com.ramcosta.composedestinations.generated.destinations.TaskListScreenDestination
import com.ramcosta.composedestinations.generated.destinations.TaskScreenDestination
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.destinations.test.core.viewmodel.viewModel
import com.destinations.test.tasks.presentation.details.StepDetailsViewModel
import com.destinations.test.tasks.presentation.details.TaskDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    destination: DestinationSpec,
    navBackStackEntry: NavBackStackEntry?,
) {
    TopAppBar(title = {
        Spacer(Modifier.width(8.dp))

        Text(
            text = destination.topBarTitle(navBackStackEntry),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
    })
}

@Composable
fun DestinationSpec.topBarTitle(navBackStackEntry: NavBackStackEntry?): String {
    return when (this) {
        TaskScreenDestination -> {
            // Here you can also call another Composable on another file like TaskScreenTopBar
            // 👇 access the same viewmodel instance the screen is using, by passing the back stack entry
            val viewModel = navBackStackEntry?.let {
                viewModel<TaskDetailsViewModel>(
                    navBackStackEntry,
                    navBackStackEntry
                )
            }
            val task = viewModel?.let { viewModel.task.collectAsState().value }
            task?.title ?: ""
        }

        StepScreenDestination -> {
            // Here you can also call another Composable on another file like StepScreenTopBar
            // 👇 access the same viewmodel instance the screen is using, by passing the back stack entry
            val viewModel = navBackStackEntry?.let {
                viewModel<StepDetailsViewModel>(
                    navBackStackEntry,
                    navBackStackEntry
                )
            }
            val step = viewModel?.let {
                viewModel.step.collectAsState().value
            }
            val task = viewModel?.let {
                viewModel.task.collectAsState().value
            }
            "${task?.title ?: ""}: ${step?.title ?: ""}"
        }

        AddStepDialogDestination,
        AddTaskDialogDestination,
        AccountScreenDestination,
        LoginScreenDestination,
        SettingsScreenDestination,
        TaskListScreenDestination,
            -> javaClass.simpleName.removeSuffix("Destination")

        else -> error("unknown Destination $this")
    }
}