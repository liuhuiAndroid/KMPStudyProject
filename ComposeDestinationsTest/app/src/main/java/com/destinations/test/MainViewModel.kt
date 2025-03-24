package com.destinations.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.destinations.test.login.data.LoginStateRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val loginStateRepository: LoginStateRepository,
) : ViewModel() {

    val isLoggedInFlow = loginStateRepository.isLoggedIn

    val isLoggedIn get() = isLoggedInFlow.value

    fun login() {
        viewModelScope.launch {
            loginStateRepository.login()
        }
    }
}