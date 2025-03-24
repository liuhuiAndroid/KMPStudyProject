package com.destinations.test.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.destinations.test.login.data.LoginStateRepository
import kotlinx.coroutines.launch

class AccountViewModel(
    private val loginStateRepository: LoginStateRepository
) : ViewModel() {

    fun onLogoutClick() {
        viewModelScope.launch {
            loginStateRepository.logout()
        }
    }
}