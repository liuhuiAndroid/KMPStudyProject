package com.destinations.test.tasks.presentation.new

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.destinations.test.core.viewmodel.viewModel
import com.destinations.test.ui.composables.TitleConfirmDialog

data class AddStepDialogNavArgs(
    val taskId: Int
)

@Destination<RootGraph>(
    style = DestinationStyle.Dialog::class,
    navArgs = AddStepDialogNavArgs::class
)
@Composable
fun AddStepDialog(
    navigator: DestinationsNavigator,
    viewModel: AddStepViewModel = viewModel()
) {
    TitleConfirmDialog(
        type = "step", //use string resources in a real app ofc :)
        title = viewModel.title.collectAsState().value,
        onTitleChange = viewModel::onTitleChange,
        onConfirm = {
            viewModel.onConfirmClicked()
            navigator.popBackStack()
        }
    )
}
