package com.example.corona.domain.usecases

import com.example.corona.domain.repository.CoronaRepository

class GetLocationStatByCode(
    private val repository: CoronaRepository
) {
    suspend operator fun invoke(code: String) = repository.getLocationStatByCode(code)
}