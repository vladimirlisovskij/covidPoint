package com.example.corona.domain.repository

import com.example.corona.domain.model.dto.LocationMarkerInfoDto
import com.example.corona.domain.model.dto.LocationStatInfoDto
import kotlinx.coroutines.flow.Flow

interface CoronaRepository {
    fun getAllLocationsMarkers(): Flow<List<LocationMarkerInfoDto>>
    fun getAllLocationsStats(): Flow<List<LocationStatInfoDto>>

    suspend fun  getLocationStatByCode(code: String): LocationStatInfoDto

    suspend fun updateDb()
}

