package com.example.corona.presentation.ui.main

import androidx.lifecycle.viewModelScope
import com.example.corona.domain.usecases.UpdateLocationsCase
import com.example.corona.presentation.base.view.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val updateLocationsCase: UpdateLocationsCase,
) : BaseViewModel() {
    private val _tab = MutableStateFlow(MainFlowTabs.MAP_TAB)
    val tab = _tab.asStateFlow()


    private val _progressDialogIsVisible = MutableStateFlow(false)
    val progressDialogIsVisible = _progressDialogIsVisible.asStateFlow()


    fun onUpdate() {
        viewModelScope.launch {
//            _progressDialogIsVisible.value = true
            updateLocationsCase()
//            _progressDialogIsVisible.value = false
        }
    }


    fun onMapTabClick() {
        _tab.value = MainFlowTabs.MAP_TAB
    }


    fun onListTabClick() {
        _tab.value = MainFlowTabs.LIST_TAB
    }


    enum class MainFlowTabs {
        MAP_TAB,
        LIST_TAB
    }
}