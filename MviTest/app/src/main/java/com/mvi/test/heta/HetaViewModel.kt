package com.mvi.test.heta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.test.network.HetaDataSource
import com.mvi.test.network.NetworkError
import com.mvi.test.network.onError
import com.mvi.test.network.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HetaScreenState(
    val timeStamp: String = "",
    val dxSecretByDeviceCode: String = "",
    val isLoading: Boolean = false,
)

sealed class HetaUiIntent {
    data object ClickGetCurrentTime : HetaUiIntent()
    data object ClickQueryDxSecretByDeviceCode : HetaUiIntent()
}

sealed interface HetaEvent {
    data class Error(val error: NetworkError) : HetaEvent
}

class HetaViewModel(
    private val hetaDataSource: HetaDataSource,
) : ViewModel() {
    private val _state = MutableStateFlow(HetaScreenState())

    val state: StateFlow<HetaScreenState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = HetaScreenState()
    )

    private val _events = Channel<HetaEvent>()
    val events = _events.receiveAsFlow()


    fun onAction(intent: HetaUiIntent) {
        when (intent) {
            is HetaUiIntent.ClickGetCurrentTime -> {
                getCurrentTime()
            }

            is HetaUiIntent.ClickQueryDxSecretByDeviceCode -> {
                queryDxSecretByDeviceCode()
            }
        }
    }

    private fun getCurrentTime() {
        viewModelScope.launch {
            hetaDataSource
                .getCurrentTime()
                .onSuccess { value ->
                    _state.update {
                        it.copy(
                            timeStamp = value
                        )
                    }
                }
                .onError { error ->
                    _events.send(HetaEvent.Error(error))
                }
        }
    }

    private fun queryDxSecretByDeviceCode() {
        viewModelScope.launch {
            hetaDataSource
                .queryDxSecretByDeviceCode()
                .onSuccess { value ->
                    _state.update {
                        it.copy(
                            dxSecretByDeviceCode = value
                        )
                    }
                }
                .onError { error ->
                    _events.send(HetaEvent.Error(error))
                }
        }
    }
}