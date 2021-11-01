package com.example.corona.data.models.dto

import com.google.gson.annotations.SerializedName

data class LocationApiResponse(
    val locations: List<LocationApiInfo>
)

data class LocationApiInfo(
    val id: Int,
    val country: String,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("country_population") val countryPopulation: Int,
    val latest: LocationApiStatus,
    val coordinates: LocationApiCoordinates
)

data class LocationApiStatus(
    val confirmed: Int,
    val deaths: Int,
    val recovered: Int
)


data class LocationApiCoordinates(
    val latitude: String,
    val longitude: String,
)