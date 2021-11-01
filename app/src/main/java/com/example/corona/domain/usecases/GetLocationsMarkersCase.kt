package com.example.corona.domain.usecases

import com.example.corona.domain.repository.CoronaRepository

class GetLocationsMarkersCase(
    private val repository: CoronaRepository
) {
    operator fun invoke() = repository.getAllLocationsMarkers()
}

