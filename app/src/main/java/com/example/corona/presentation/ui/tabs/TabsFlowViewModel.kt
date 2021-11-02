package com.example.corona.presentation.ui.tabs

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.corona.domain.exceptions.TimeOutException
import com.example.corona.domain.usecases.UpdateLocationsCase
import com.example.corona.presentation.base.view.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TabsFlowViewModel(
    private val updateLocationsCase: UpdateLocationsCase,
) : BaseViewModel() {
    private val _tab = MutableStateFlow(MainFlowTabs.MAP_TAB)
    val tab = _tab.asStateFlow()

    fun onUpdate() {
        viewModelScope.launch {
            try {
                updateLocationsCase()
            } catch (e: TimeOutException) {
                Log.d("tag", e.toString())
            }
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