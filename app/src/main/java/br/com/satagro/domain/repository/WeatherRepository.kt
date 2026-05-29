package br.com.satagro.domain.repository

import br.com.satagro.domain.common.Resource
import br.com.satagro.domain.model.WeatherData

interface WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double): Resource<WeatherData>
}
