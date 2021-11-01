package com.example.corona.data.utils

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class DoubleAdapter: TypeAdapter<Double?>() {
    override fun write(out: JsonWriter, value: Double?) {
        out.value(value)
    }

    override fun read(reader: JsonReader): Double? {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null;
        }

        return reader.nextString().toDoubleOrNull()
    }
}