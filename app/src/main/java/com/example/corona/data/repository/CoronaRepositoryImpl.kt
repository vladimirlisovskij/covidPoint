package com.example.corona.data.repository

import com.example.corona.data.models.api.CoronaApi
import com.example.corona.data.models.database.CoronaDAO
import com.example.corona.data.models.dto.LocationEntity
import com.example.corona.data.models.dto.LocationMarkerDb
import com.example.corona.data.models.dto.LocationStatsDb
import com.example.corona.data.models.urls.FlagUrlsModel
import com.example.corona.data.utils.toDB
import com.example.corona.data.utils.toDomain
import com.example.corona.domain.model.dto.LocationMarkerInfoDto
import com.example.corona.domain.model.dto.LocationStatInfoDto
import com.example.corona.domain.repository.CoronaRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
            val response = coronaApi.getAllLocations()

            response.body()?.locations?.let { list ->
                val locations = list.distinctBy { it.countryCode }

                val fanOut = produce { locations.forEach { send(it) } }
                val fanIn = produce {
                    repeat(20) {
                        launch {
                            for (location in fanOut) {
                                var confirmed = 0
                                var deaths = 0
                                var recovered = 0
                                var lat = 0.0
                                var lon = 0.0
                                var regionCount = 0

                                list.filter { it.countryCode == location.countryCode }
                                    .forEach {
                                        confirmed += it.latest.confirmed
                                        deaths += it.latest.deaths
                                        recovered += it.latest.recovered

                                        val latStr = it.coordinates.latitude.toDoubleOrNull()
                                        val lonStr = it.coordinates.longitude.toDoubleOrNull()

                                        if (latStr != null && lonStr != null) {
                                            lat += latStr
                                            lon += lonStr

                                            regionCount++
                                        }
                                    }
                                regionCount = max(1, regionCount)

                                send(
                                    location.toDB(
                                        confirmed,
                                        deaths,
                                        recovered,
                                        lat / regionCount,
                                        lon / regionCount
                                    )
                                )
                            }
                        }
                    }
                }

                val res = mutableListOf<LocationEntity>()
                for (location in fanIn) {
                    res.add(location)
                }
                coronaDAO.insertAllLocations(res)
            }
        }
    }

    override suspend fun getLocationStatByCode(code: String): LocationStatInfoDto {
        return withContext(dispatcherIO) {
            coronaDAO.getLocationStatsByCode(code).toDomain(urlsModel.getFlagUrlByCode(code))
        }
    }
}