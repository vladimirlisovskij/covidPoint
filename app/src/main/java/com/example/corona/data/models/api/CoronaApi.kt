package com.example.corona.data.models.api

import com.example.corona.data.models.dto.LocationApiResponse
import com.example.corona.data.models.dto.LocationApiInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoronaApi {
    @GET("/v2/locations")
    suspend fun getAllLocations(): Response<LocationApiResponse>

    @GET("/v2/locations/{id}")
    suspend fun getLocationById(@Path(value = "id") id: Int): Response<LocationApiInfo>
}