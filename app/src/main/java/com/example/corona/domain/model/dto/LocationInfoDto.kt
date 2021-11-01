package com.example.corona.domain.model.dto

data class LocationInfoDto(
    val id: Int,
    val country: String,
    val countryCode: String,
    val countryPopulation: Int,
    val confirmed: Int,
    val deaths: Int,
    val recovered: Int,
    val lat: Double,
    val lon: Double
)


data class LocationMarkerInfoDto(
    val countryCode: String,
    val confirmed: Int,
    val lat: Double,
    val lon: Double
)

data class LocationStatInfoDto(
    val url: String,
    val countryCode: String,
    val country: String,
    val confirmed: Int,
    val deaths: Int,
    val recovered: Int,
)
