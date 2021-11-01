package com.example.corona.data.utils

import com.example.corona.data.models.dto.LocationApiInfo
import com.example.corona.data.models.dto.LocationEntity
import com.example.corona.data.models.dto.LocationMarkerDb
import com.example.corona.data.models.dto.LocationStatsDb
import com.example.corona.domain.model.dto.LocationInfoDto
import com.example.corona.domain.model.dto.LocationMarkerInfoDto
import com.example.corona.domain.model.dto.LocationStatInfoDto

fun LocationEntity.toDomain(): LocationInfoDto {
    return LocationInfoDto(
        id = id,
        country = country,
        countryCode = countryCode,
        countryPopulation = countryPopulation,
        confirmed = confirmed,
        deaths = deaths,
        recovered = recovered,
        lat = lat,
        lon = lon
    )
}

fun LocationMarkerDb.toDomain(): LocationMarkerInfoDto {
    return LocationMarkerInfoDto(
        countryCode = countryCode,
        confirmed = confirmed,
        lat = lat,
        lon = lon
    )
}

fun LocationStatsDb.toDomain(url: String): LocationStatInfoDto {
    return LocationStatInfoDto(
        url = url,
        country = country,
        countryCode = countryCode,
        confirmed = confirmed,
        deaths = deaths,
        recovered = recovered
    )
}

fun LocationApiInfo.toDB(
    confirmed: Int,
    death: Int,
    recovered: Int,
    lat: Double,
    lon: Double
): LocationEntity {
    return LocationEntity(
        id = id,
        country = country,
        countryCode = countryCode,
        countryPopulation = countryPopulation,
        confirmed = confirmed,
        deaths = death,
        recovered = recovered,
        lat = lat,
        lon = lon
    )
}