package com.example.corona.data.models.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity(
    @PrimaryKey val id: Int,
    val country: String,
    val countryCode: String,
    val countryPopulation: Int,
    val confirmed: List<Int>,
    val deaths: List<Int>,
    val recovered: List<Int>,
    val lat: Double,
    val lon: Double
)

data class LocationMarkerDb(
    val countryCode: String,
    val confirmed: List<Int>,
    val lat: Double,
    val lon: Double
)

data class LocationStatsDb(
    val countryCode: String,
    val country: String,
    val confirmed: List<Int>,
    val deaths: List<Int>,
    val recovered: List<Int>,
)