package br.com.satagro.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.satagro.domain.model.WeatherData

@Composable
fun WeatherCard(
    weather: WeatherData,
    isTechnicalMode: Boolean,
    regionName: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = regionName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(Modifier.height(12.dp))

            if (isTechnicalMode) {
                WeatherTechnicalView(weather)
            } else {
                WeatherSimpleView(weather)
            }
        }
    }
}

@Composable
private fun WeatherSimpleView(weather: WeatherData) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = if (weather.weatherCode < 3) Icons.Filled.WbSunny else Icons.Filled.WbCloudy,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = "${weather.temperatureCurrent.toInt()}°C",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = weatherDescription(weather),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun WeatherTechnicalView(weather: WeatherData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        WeatherMetric(Icons.Filled.Thermostat, "${weather.temperatureCurrent}°C", "Temperatura")
        WeatherMetric(Icons.Filled.WaterDrop, "${weather.precipitation}mm", "Precipitação")
        WeatherMetric(Icons.Filled.Air, "${weather.windspeed}km/h", "Vento")
    }
    Spacer(Modifier.height(8.dp))
    Text(
        text = "WMO Code: ${weather.weatherCode}",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
}

@Composable
private fun WeatherMetric(icon: androidx.compose.ui.graphics.vector.ImageVector, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = label, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
        Text(text = value, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
    }
}

private fun weatherDescription(weather: WeatherData): String = when {
    weather.weatherCode == 0 -> "Céu limpo. Condições excelentes para trabalho no campo."
    weather.weatherCode in 1..3 -> "Nublado. Temperatura agradável para atividades agrícolas."
    weather.weatherCode in 51..67 -> "Chuva leve a moderada. Bom para irrigação natural."
    weather.weatherCode in 71..77 -> "Precipitação. Monitore drenagem das lavouras."
    weather.weatherCode in 80..99 -> "Chuvas intensas. Risco para operações de campo."
    else -> "Condições variáveis. Acompanhe a previsão estendida."
}
