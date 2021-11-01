package com.example.corona.presentation.utils

import com.example.corona.domain.model.dto.LocationMarkerInfoDto
import com.example.corona.domain.model.dto.LocationStatInfoDto
import com.example.corona.presentation.model.dto.MarkerInfoUiDto
import com.example.corona.presentation.model.dto.StatsInfoUiDto

fun LocationMarkerInfoDto.toUi(): MarkerInfoUiDto {
    return MarkerInfoUiDto(
        countryCode = countryCode,
        confirmed = confirmed,
        lat = lat,
        lon = lon
    )
}


fun LocationStatInfoDto.toUi(): StatsInfoUiDto {
    return StatsInfoUiDto(
        name = country,
        confirmed = confirmed,
        deaths = deaths,
        recovered = recovered,
        countryCode = countryCode,
        flagUrl = url
    )
}