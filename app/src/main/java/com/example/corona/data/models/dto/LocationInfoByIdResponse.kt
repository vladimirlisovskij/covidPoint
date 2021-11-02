package com.example.corona.data.models.dto

import com.google.gson.annotations.SerializedName

data class LocationInfoByIdResponse(
    val location: LocationInfo
)

data class LocationInfo(
    val id: Int,
    val country: String,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("country_population") val countryPopulation: Int,
    val timelines: LocationTimeLine,
    val coordinates: LocationApiCoordinates
)

data class LocationApiCoordinates(
    val latitude: String,
    val longitude: String,
)

data class LocationTimeLine(
    val deaths: TimeLine,
    val confirmed: TimeLine,
    val recovered: TimeLine
)

data class TimeLine(
    val timeline: Map<String, Int>
)