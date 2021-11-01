package com.example.corona.presentation.ui.mapTab.coronaMap

import androidx.lifecycle.viewModelScope
import com.example.corona.domain.model.dto.LocationMarkerInfoDto
import com.example.corona.domain.usecases.GetLocationStatByCode
import com.example.corona.domain.usecases.GetLocationsMarkersCase
import com.example.corona.presentation.base.view.BaseViewModel
import com.example.corona.presentation.model.dto.StatsInfoUiDto
import com.example.corona.presentation.utils.toUi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CoronaViewModel(
    private val getLocationsMarkersCase: GetLocationsMarkersCase,
    private val getLocationStatByCode: GetLocationStatByCode,
) : BaseViewModel() {
    val locations = getLocationsMarkersCase().map { it.map(LocationMarkerInfoDto::toUi) }

    private val _screenEvents = MutableSharedFlow<ScreenEvents>()
    val screenEvents = _screenEvents.asSharedFlow()

    fun onMarkerClick(code: String) {
        viewModelScope.launch {
            val location = getLocationStatByCode(code)

            _screenEvents.emit(
                ScreenEvents.ShowLocationInfoDialog(location.toUi())
            )
        }
    }

    fun onMoveToLocation(code: String) {
        viewModelScope.launch {
            locations.first().firstOrNull {
                it.countryCode == code
            }?.let {
                _screenEvents.emit(ScreenEvents.MoveCamera(it.lat, it.lon))
            }
        }
    }


    sealed class ScreenEvents {
        data class ShowLocationInfoDialog(
            val data: StatsInfoUiDto
        ) : ScreenEvents()

        data class MoveCamera(
            val lat: Double,
            val lon: Double
        ): ScreenEvents()
    }
}