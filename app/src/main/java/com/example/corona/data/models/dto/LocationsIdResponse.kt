package com.example.corona.data.models.dto

import com.google.gson.annotations.SerializedName

data class LocationsIdResponse(
    val locations: List<LocationIdInfo>
)

data class LocationIdInfo(
    val id: Int,
    val country: String,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("country_population") val countryPopulation: Int,
)