package br.com.satagro.domain.usecase

import br.com.satagro.domain.common.Resource
import br.com.satagro.domain.model.WeatherData
import br.com.satagro.domain.repository.WeatherRepository

class GetWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(lat: Double, lon: Double): Resource<WeatherData> =
        repository.getWeather(lat, lon)
}
