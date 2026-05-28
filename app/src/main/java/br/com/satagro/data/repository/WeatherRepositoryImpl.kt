package br.com.satagro.data.repository

import br.com.satagro.data.model.toDomain
import br.com.satagro.data.remote.WeatherRemoteDataSource
import br.com.satagro.domain.common.Resource
import br.com.satagro.domain.model.WeatherData
import br.com.satagro.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val remoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {
    override suspend fun getWeather(latitude: Double, longitude: Double): Resource<WeatherData> =
        try {
            val dto = remoteDataSource.getWeather(latitude, longitude)
            Resource.Success(dto.toDomain())
        } catch (e: Exception) {
            Resource.Error("Não foi possível carregar os dados climáticos.")
        }
}
