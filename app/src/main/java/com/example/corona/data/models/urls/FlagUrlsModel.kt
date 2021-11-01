package com.example.corona.data.models.urls

import com.example.corona.data.utils.Constants

class FlagUrlsModel {
    fun getFlagUrlByCode(code: String): String {
        return Constants.FLAG_BASE_URL + "/120x90/${code.lowercase()}.png"
    }
}