package br.com.satagro.data.remote

import br.com.satagro.data.remote.api.OpenMeteoApi
import br.com.satagro.data.remote.dto.WeatherResponseDto

class WeatherRemoteDataSourceImpl(
    private val api: OpenMeteoApi
) : WeatherRemoteDataSource {
    override suspend fun getWeather(latitude: Double, longitude: Double): WeatherResponseDto =
        api.getWeather(latitude, longitude)
}
