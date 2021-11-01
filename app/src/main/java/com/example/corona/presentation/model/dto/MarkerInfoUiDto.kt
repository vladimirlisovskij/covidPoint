package com.example.corona.presentation.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MarkerInfoUiDto(
    val countryCode: String,
    val confirmed: Int,
    val lat: Double,
    val lon: Double
)

@Parcelize
data class StatsInfoUiDto(
    val flagUrl: String,
    val countryCode: String,
    val name: String,
    val confirmed: Int,
    val deaths: Int,
    val recovered: Int
): Parcelable
