package com.destinations.test.tasks.presentation.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.AddStepDialogDestination
import com.ramcosta.composedestinations.generated.destinations.StepScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.destinations.test.core.viewmodel.viewModel
import com.destinations.test.tasks.domain.Step

@Destination<RootGraph>(navArgs = TaskScreenNavArgs::class)
@Composable
fun TaskScreen(
    navArgs: TaskScreenNavArgs,
    navigator: DestinationsNavigator,
    viewModel: TaskDetailsViewModel = viewModel()
) {
    val task = viewModel.task.collectAsState().value

    if (task == null) {
        CircularProgressIndicator()
        return
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigator.navigate(AddStepDialogDestination(navArgs.taskId)) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add step button",
                    tint = Color.White
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Completed:")

                Checkbox(
                    checked = task.completed,
                    onCheckedChange = viewModel::onTaskCheckedChange
                )
            }

            Spacer(Modifier.height(16.dp))

            Text("Steps:")

            val steps = viewModel.steps.collectAsState().value
            LazyColumn {
                items(steps) { step ->
                    StepItem(
                        step = step,
                        onStepClicked = {
                            navigator.navigate(StepScreenDestination(step.id))
                        },
                        onCheckedChange = { viewModel.onStepCheckedChanged(step, it) }
                    )
                }
            }
        }
    }
}

@Composable
fun StepItem(
    step: Step,
    onStepClicked: () -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onStepClicked() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = step.completed,
            onCheckedChange = onCheckedChange
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(step.title)
    }
}
