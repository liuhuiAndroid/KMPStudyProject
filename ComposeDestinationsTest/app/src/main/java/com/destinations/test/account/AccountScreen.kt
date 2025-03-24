package com.destinations.test.account

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.destinations.test.core.viewmodel.viewModel

@Destination<RootGraph>
@Composable
fun AccountScreen(
    vm: AccountViewModel = viewModel(),
) {
    Button(onClick = { vm.onLogoutClick() }, modifier = Modifier.wrapContentSize()) {
        Text("Logout")
    }
}