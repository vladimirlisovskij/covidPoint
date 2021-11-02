package com.example.corona.data.utils

import com.example.corona.data.models.dto.LocationMarkerDb
import com.example.corona.data.models.dto.LocationStatsDb
import com.example.corona.domain.model.dto.LocationMarkerInfoDto
import com.example.corona.domain.model.dto.LocationStatInfoDto

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