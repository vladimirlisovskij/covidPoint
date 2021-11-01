package com.example.corona.presentation.ui.listTab.coronaList

import androidx.lifecycle.viewModelScope
import com.example.corona.domain.model.dto.LocationStatInfoDto
import com.example.corona.domain.usecases.GetLocationsStatsCase
import com.example.corona.presentation.base.view.BaseViewModel
import com.example.corona.presentation.utils.toUi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CoronaListViewModel(
    private val getLocationsStatsCase: GetLocationsStatsCase
) : BaseViewModel() {
    val locationsStats = getLocationsStatsCase().map { it.map(LocationStatInfoDto::toUi) }

    private val _screenEvents = MutableSharedFlow<ScreenEvents>()
    val screenEvents = _screenEvents.asSharedFlow()

    fun onLocationClick(code: String) {
        viewModelScope.launch {
            _screenEvents.emit(ScreenEvents.NavigateToMapScreen(code))
        }
    }

    sealed class ScreenEvents{
        data class NavigateToMapScreen(val code: String): ScreenEvents()
    }
}