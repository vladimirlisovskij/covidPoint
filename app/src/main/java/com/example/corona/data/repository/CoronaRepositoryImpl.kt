package com.example.corona.data.repository

import com.example.corona.data.models.api.CoronaApi
import com.example.corona.data.models.database.CoronaDAO
import com.example.corona.data.models.dto.LocationEntity
import com.example.corona.data.models.dto.LocationIdInfo
import com.example.corona.data.models.dto.LocationInfo
import com.example.corona.data.models.dto.LocationMarkerDb
import com.example.corona.data.models.urls.FlagUrlsModel
import com.example.corona.data.utils.toDomain
import com.example.corona.domain.model.dto.LocationMarkerInfoDto
import com.example.corona.domain.model.dto.LocationStatInfoDto
import com.example.corona.domain.repository.CoronaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.max

class CoronaRepositoryImpl(
    private val coronaApi: CoronaApi,
    private val coronaDAO: CoronaDAO,
    private val urlsModel: FlagUrlsModel,
    private val dispatcherIO: CoroutineDispatcher
) : CoronaRepository {
    override fun getAllLocationsMarkers(): Flow<List<LocationMarkerInfoDto>> {
        return coronaDAO.getLocationsMarkerInfo().map { it.map(LocationMarkerDb::toDomain) }
    }

    override fun getAllLocationsStats(): Flow<List<LocationStatInfoDto>> {
        return coronaDAO.getAllLocationsStats()
            .map {
                it.map {
                    it.toDomain(urlsModel.getFlagUrlByCode(it.countryCode))
                }
            }
    }

    override suspend fun updateDb() {
        withContext(dispatcherIO) {
            val response = coronaApi.getLocationsId()
            response.body()?.locations?.let { list ->
                val result = mutableListOf<LocationEntity>();
                for (entity in createCountyChannel(this, list)) {
                    result.add(entity)
                }
                coronaDAO.insertAllLocations(result)
            }
        }
    }

    override suspend fun getLocationStatByCode(code: String): LocationStatInfoDto {
        return withContext(dispatcherIO) {
            coronaDAO.getLocationStatsByCode(code).toDomain(urlsModel.getFlagUrlByCode(code))
        }
    }

    private fun createCountyChannel(scope: CoroutineScope, locations: List<LocationIdInfo>): ReceiveChannel<LocationEntity> {
        val fanOut = scope.produce { locations.distinctBy { it.countryCode }.forEach { send(it) } }
        return scope.produce {
            repeat(20) {
                launch {
                    for (location in fanOut) {
                        val deathsProgression = Array(DYNAMIC_LENGTH) { 0 }
                        val recoveredProgression = Array(DYNAMIC_LENGTH) { 0 }
                        val confirmedProgression = Array(DYNAMIC_LENGTH) { 0 }
                        var lat = 0.0
                        var lon = 0.0
                        var regionCount = 0
                        for (province in createProvinceChannel(scope, location, locations)) {
                            val latStr = province.coordinates.latitude.toDoubleOrNull()
                            val lonStr = province.coordinates.longitude.toDoubleOrNull()

                            if (latStr != null && lonStr != null) {
                                lat += latStr
                                lon += lonStr
                                regionCount++
                            }

                            province.timelines.confirmed.timeline
                                .map { (_, value) -> value }
                                .takeLast(DYNAMIC_LENGTH)
                                .forEachIndexed { index, data ->
                                    confirmedProgression[index] += data
                                }

                            province.timelines.deaths.timeline
                                .map { (_, value) -> value }
                                .takeLast(DYNAMIC_LENGTH)
                                .forEachIndexed { index, data ->
                                    deathsProgression[index] += data
                                }

                            province.timelines.recovered.timeline
                                .map { (_, value) -> value }
                                .takeLast(DYNAMIC_LENGTH)
                                .forEachIndexed { index, data ->
                                    recoveredProgression[index] += data
                                }
                        }

                        regionCount = max(1, regionCount)
                        send(
                            LocationEntity(
                                id = location.id,
                                country = location.country,
                                countryCode = location.countryCode,
                                countryPopulation = location.countryPopulation,
                                confirmed = confirmedProgression.toList(),
                                deaths = deathsProgression.toList(),
                                recovered = recoveredProgression.toList(),
                                lat = lat / regionCount,
                                lon = lon / regionCount,
                            )
                        )
                    }
                }
            }
        }
    }

    private fun createProvinceChannel(
        scope: CoroutineScope,
        country: LocationIdInfo,
        locations: List<LocationIdInfo>
    ): ReceiveChannel<LocationInfo> {
        val fanOut = scope.produce {
            locations.filter { it.countryCode == country.countryCode }
                .forEach { send(it) }
        }
        return scope.produce {
            repeat(10) {
                launch {
                    for (province in fanOut) {
                        coronaApi.getLocationById(province.id).body()?.let {
                            send(it.location)
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val DYNAMIC_LENGTH = 30
    }
}