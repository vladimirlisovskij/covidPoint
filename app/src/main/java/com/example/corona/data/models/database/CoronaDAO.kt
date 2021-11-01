package com.example.corona.data.models.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.corona.data.models.dto.LocationEntity
import com.example.corona.data.models.dto.LocationMarkerDb
import com.example.corona.data.models.dto.LocationStatsDb
import kotlinx.coroutines.flow.Flow

@Dao
interface CoronaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(data: LocationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLocations(data: List<LocationEntity>)

    @Query("select countryCode, confirmed, lat, lon from locationEntity")
    fun getLocationsMarkerInfo(): Flow<List<LocationMarkerDb>>

    @Query( "select * from locationentity")
    fun getAllLocationsStats(): Flow<List<LocationStatsDb>>

    @Query("select * from locationentity where countryCode = :code")
    fun getLocationStatsByCode(code: String): LocationStatsDb
}

