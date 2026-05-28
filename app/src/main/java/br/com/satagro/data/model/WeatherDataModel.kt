package br.com.satagro.data.model

import br.com.satagro.data.remote.dto.WeatherResponseDto
import br.com.satagro.domain.model.WeatherData

fun WeatherResponseDto.toDomain() = WeatherData(
    temperatureCurrent = current.temperature,
    precipitation = current.precipitation,
    windspeed = current.windSpeed,
    weatherCode = current.weatherCode,
    dailyMaxTemps = daily.maxTemps,
    dailyMinTemps = daily.minTemps,
    dailyPrecipitation = daily.precipitation
)
