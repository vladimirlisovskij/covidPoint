package com.example.corona.presentation.ui.main

import androidx.lifecycle.viewModelScope
import com.example.corona.presentation.base.view.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {
    private val _screenEvents = MutableSharedFlow<ScreenEvents>()
    val screenEvents = _screenEvents.asSharedFlow()


    fun onCreate() {
        viewModelScope.launch {
            delay(2000)
            _screenEvents.emit(ScreenEvents.OpenMainFlowScreen)
        }
    }


    sealed class ScreenEvents() {
        object OpenMainFlowScreen : ScreenEvents()
    }
}