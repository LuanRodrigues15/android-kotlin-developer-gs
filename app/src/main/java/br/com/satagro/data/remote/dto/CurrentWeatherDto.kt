package br.com.satagro.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDto(
    @SerialName("temperature_2m") val temperature: Double,
    @SerialName("precipitation") val precipitation: Double,
    @SerialName("weathercode") val weatherCode: Int,
    @SerialName("windspeed_10m") val windSpeed: Double
)
