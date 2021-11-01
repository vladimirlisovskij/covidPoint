package com.example.corona.domain.di

import com.example.corona.domain.usecases.*
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetLocationsMarkersCase(
            repository = get()
        )
    }

    factory {
        UpdateLocationsCase(
            repository = get()
        )
    }

    factory {
        GetLocationStatByCode(
            repository = get()
        )
    }

    factory {
        GetLocationsStatsCase(
            repository = get()
        )
    }
}

val domainModule = useCaseModule