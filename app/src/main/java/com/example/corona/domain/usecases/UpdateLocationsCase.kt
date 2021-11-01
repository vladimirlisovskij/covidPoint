package com.example.corona.domain.usecases

import com.example.corona.domain.repository.CoronaRepository

class UpdateLocationsCase(
    private val repository: CoronaRepository
) {
    suspend operator fun invoke() = repository.updateDb()
}