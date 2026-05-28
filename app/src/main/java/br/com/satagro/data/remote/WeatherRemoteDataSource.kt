package br.com.satagro.data.remote

import br.com.satagro.data.remote.dto.WeatherResponseDto

interface WeatherRemoteDataSource {
    suspend fun getWeather(latitude: Double, longitude: Double): WeatherResponseDto
}
