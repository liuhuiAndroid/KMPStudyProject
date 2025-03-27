package com.load.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading
        .onStart {
            loadData()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )

    // StateFlow ≈ 带初始值的 SharedFlow(replay=1) + 自动去重。
    // 选择取决于是否需要维护状态（StateFlow）还是广播事件（SharedFlow）
//    val isLoading = _isLoading.asStateFlow()

//    init {
//        loadData()
//    }

    fun loadData() {
        println("Loading data ...")
        viewModelScope.launch {
            _isLoading.value = true
            delay(1000L)
            _isLoading.value = false
        }
    }
}