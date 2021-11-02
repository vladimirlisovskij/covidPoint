package com.example.corona.data.di

import androidx.room.Room
import com.example.corona.data.models.api.CoronaApi
import com.example.corona.data.models.database.CoronaDAO
import com.example.corona.data.models.database.CoronaDataBase
import com.example.corona.data.models.urls.FlagUrlsModel
import com.example.corona.data.repository.CoronaRepositoryImpl
import com.example.corona.data.utils.Constants
import com.example.corona.data.utils.DoubleAdapter
import com.example.corona.domain.repository.CoronaRepository
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    single {
        val gson = GsonBuilder().registerTypeAdapter(Double::class.java, DoubleAdapter()).create()
        GsonConverterFactory.create(gson)
    }

    single {
        OkHttpClient.Builder()
//            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

val modelModule = module {
    fun createCoronaApi(retrofit: Retrofit): CoronaApi {
        return retrofit.create(CoronaApi::class.java)
    }

    fun getCoronaDAO(db: CoronaDataBase): CoronaDAO {
        return db.getCoronaDAO()
    }

    single { createCoronaApi(get()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            CoronaDataBase::class.java,
            Constants.DB_NAME
        ).build()
    }

    single { getCoronaDAO(get()) }

    single { FlagUrlsModel() }
}

val dispatchersModule = module {
    single(named(Constants.DISPATCHER_IO)) { Dispatchers.IO }
}

val repositoryModule = module {
    single<CoronaRepository> {
        CoronaRepositoryImpl(
            coronaApi = get(),
            coronaDAO = get(),
            urlsModel = get(),
            dispatcherIO = get(named(Constants.DISPATCHER_IO))
        )
    }
}

val dataModule = networkModule + modelModule + dispatchersModule + repositoryModule