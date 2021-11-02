package com.example.corona.presentation.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MarkerInfoUiDto(
    val countryCode: String,
    val confirmed: List<Int>,
    val lat: Double,
    val lon: Double
)

@Parcelize
data class StatsInfoUiDto(
    val flagUrl: String,
    val countryCode: String,
    val name: String,
    val confirmed: List<Int>,
    val deaths: List<Int>,
    val recovered: List<Int>
): Parcelable
