package br.com.satagro.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyWeatherDto(
    @SerialName("temperature_2m_max") val maxTemps: List<Double>,
    @SerialName("temperature_2m_min") val minTemps: List<Double>,
    @SerialName("precipitation_sum") val precipitation: List<Double>
)
