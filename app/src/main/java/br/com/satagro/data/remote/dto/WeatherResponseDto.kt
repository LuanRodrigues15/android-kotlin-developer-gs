package br.com.satagro.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseDto(
    val current: CurrentWeatherDto,
    val daily: DailyWeatherDto
)
