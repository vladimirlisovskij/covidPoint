package com.example.corona.data.models.database

import androidx.room.TypeConverter

class Converter {
    @TypeConverter
    fun fromList(data: List<Int>): String {
        return data.joinToString(separator = " ")
    }

    @TypeConverter
    fun toList(data: String): List<Int> {
        return data.split(" ").map { it.toInt() }
    }
}