package br.com.satagro.domain.model

data class WeatherData(
    val temperatureCurrent: Double,
    val precipitation: Double,
    val windspeed: Double,
    val weatherCode: Int,
    val dailyMaxTemps: List<Double>,
    val dailyMinTemps: List<Double>,
    val dailyPrecipitation: List<Double>
)
