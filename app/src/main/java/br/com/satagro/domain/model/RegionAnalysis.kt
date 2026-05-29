package br.com.satagro.domain.model

data class RegionAnalysis(
    val regionId: String,
    val regionName: String,
    val state: String,
    val latitude: Double,
    val longitude: Double,
    val weather: WeatherData?,
    val cultureRanking: List<CultureRanking>,
    val isFavorited: Boolean = false
)
