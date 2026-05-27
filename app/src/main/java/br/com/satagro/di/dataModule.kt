package br.com.satagro.di

import br.com.satagro.data.remote.WeatherRemoteDataSource
import br.com.satagro.data.remote.WeatherRemoteDataSourceImpl
import br.com.satagro.data.repository.WeatherRepositoryImpl
import br.com.satagro.domain.repository.WeatherRepository
import org.koin.dsl.module

val dataModule = module {

    single<WeatherRemoteDataSource> {
        WeatherRemoteDataSourceImpl(api = get())
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(remoteDataSource = get())
    }
}
