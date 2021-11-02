package com.example.corona.data.models.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.corona.data.models.dto.LocationEntity
import com.example.corona.data.utils.Constants

@Database(
    version = Constants.DB_VERSION,
    entities = [LocationEntity::class]
)
@TypeConverters(Converter::class)
abstract class CoronaDataBase : RoomDatabase() {
    abstract fun getCoronaDAO(): CoronaDAO
}