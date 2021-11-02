package com.example.corona.domain.model.dto

data class LocationMarkerInfoDto(
    val countryCode: String,
    val confirmed: List<Int>,
    val lat: Double,
    val lon: Double
)

data class LocationStatInfoDto(
    val url: String,
    val countryCode: String,
    val country: String,
    val confirmed: List<Int>,
    val deaths: List<Int>,
    val recovered: List<Int>,
)
