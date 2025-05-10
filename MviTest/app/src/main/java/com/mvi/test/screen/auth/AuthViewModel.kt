package com.mvi.test.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * https://www.youtube.com/watch?v=ZTebNp-FyYY
 * Single State Fields VS. State Data Class In Your ViewModel - What's Best?
 */
data class AuthScreenState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isPasswordValid: Boolean = false,
)

/**
 * Intentions from the UI like taps, scrolls, text changes
 * Intents goes from ui to viewmodel
 */
sealed class UiIntent {
    data class EnterUsername(val value: String) : UiIntent()
    data class EnterPassword(val value: String) : UiIntent()
    data object ClickSignIn : UiIntent()
}

/**
 * The events to be consumed by the UI like snackbar, display toast, navigate
 * UiEvents goes from viewmodel to ui
 */
sealed class UiEvent {
    data object Idle : UiEvent()
    data class ShowSnackBar(val message: String) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
}

class AuthViewModel : ViewModel() {

    // https://www.youtube.com/watch?v=JfCivo5qJkI
    // The Top 3 State Management Mistakes On Android => Use savedStateHandle
    // private val _state = savedStateHandle.getStateFlow("state", AuthScreenState())
    private val _state = MutableStateFlow(AuthScreenState())

    val state: StateFlow<AuthScreenState> = _state.onStart {
        // https://www.youtube.com/watch?v=mNKQ9dc1knI&t=13s
        // The ONLY Correct Way to Load Initial Data In Your Android App?
        // https://proandroiddev.com/loading-initial-data-in-launchedeffect-vs-viewmodel-f1747c20ce62
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = AuthScreenState()
    )
    private val _event: Channel<UiEvent> = Channel()
    val event = _event.receiveAsFlow()

    init {
        // https://www.youtube.com/watch?v=ZTebNp-FyYY
        // Single State Fields VS. State Data Class In Your ViewModel - What's Best?
        state.distinctUntilChangedBy {
            it.password
        }.map { it.password.isValidPassword() }.onEach { value ->
            _state.update { it.copy(isPasswordValid = value) }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: UiIntent) {
        when (event) {
            is UiIntent.EnterUsername -> {
                // https://www.youtube.com/watch?v=JfCivo5qJkI
                // The Top 3 State Management Mistakes On Android => Use update
                _state.update { it.copy(username = event.value) }
            }

            is UiIntent.EnterPassword -> {
                _state.update { it.copy(password = event.value) }
            }

            is UiIntent.ClickSignIn -> {
                signIn()
            }
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(800)
            _state.update { it.copy(isLoading = false) }
            if (_state.value.username.isNotEmpty() && _state.value.password.isNotEmpty()) {
                _event.send(UiEvent.Navigate("main"))
            } else {
                _event.send(UiEvent.Navigate("main")) // Test
                // _event.send(UiEvent.ShowSnackBar("Invalid credentials."))
            }
        }
    }
}

fun String.isValidPassword(): Boolean {
    return length >= 6 || true // Test
}