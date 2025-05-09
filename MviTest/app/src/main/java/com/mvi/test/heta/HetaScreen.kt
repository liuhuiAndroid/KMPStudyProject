package com.mvi.test.heta

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mvi.test.utils.ObserveAsEvents
import com.mvi.test.utils.toString
import org.koin.androidx.compose.koinViewModel

@Composable
fun HetaScreen(
    viewModel: HetaViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.events) { event ->
        when (event) {
            is HetaEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    Column {
        Text(state.timeStamp)
        Button(onClick = {
            viewModel.onAction(HetaUiIntent.ClickGetCurrentTime)
        }) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(15.dp),
                    strokeWidth = 1.dp,
                    color = Color.White
                )
            } else {
                Text("getCurrentTime")
            }
        }

        Text(state.dxSecretByDeviceCode)
        Button(onClick = {
            viewModel.onAction(HetaUiIntent.ClickQueryDxSecretByDeviceCode)
        }) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(15.dp),
                    strokeWidth = 1.dp,
                    color = Color.White
                )
            } else {
                Text("queryDxSecretByDeviceCode")
            }
        }
    }
}