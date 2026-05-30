package br.com.satagro.presentation.screens.analysis

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.satagro.domain.model.RegionAnalysis
import br.com.satagro.presentation.common.UiState
import br.com.satagro.presentation.components.ErrorMessage
import br.com.satagro.presentation.components.LoadingIndicator
import br.com.satagro.presentation.components.RankingItem
import br.com.satagro.presentation.components.WeatherCard
import br.com.satagro.presentation.viewmodel.AnalysisViewModel
import br.com.satagro.presentation.viewmodel.FavoritesViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(
    regionId: String,
    navController: NavController,
    favoritesViewModel: FavoritesViewModel
) {
    val viewModel: AnalysisViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val isTechnicalMode by viewModel.isTechnicalMode.collectAsState()
    val favoritedAnalyses by favoritesViewModel.favoritedAnalyses.collectAsState()
    val isFavorited = favoritedAnalyses.any { it.regionId == regionId }

    LaunchedEffect(regionId) { viewModel.loadAnalysis(regionId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title = if (uiState is UiState.Success) {
                        (uiState as UiState.Success<RegionAnalysis>).data.let {
                            "${it.regionName}, ${it.state}"
                        }
                    } else "Análise Regional"
                    Text(title)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (uiState is UiState.Success) {
                            val analysis = (uiState as UiState.Success<RegionAnalysis>).data
                            if (isFavorited) favoritesViewModel.removeAnalysis(regionId)
                            else favoritesViewModel.addAnalysis(analysis)
                        }
                    }) {
                        Icon(
                            imageVector = if (isFavorited) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favoritar análise",
                            tint = if (isFavorited) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { padding ->
        when (val state = uiState) {
            is UiState.Loading, is UiState.Initial ->
                LoadingIndicator(modifier = Modifier.padding(padding).fillMaxSize())
            is UiState.Error ->
                ErrorMessage(
                    message = state.message,
                    onRetry = { viewModel.retry() },
                    modifier = Modifier.padding(padding).fillMaxSize()
                )
            is UiState.Success ->
                AnalysisContent(
                    analysis = state.data,
                    isTechnicalMode = isTechnicalMode,
                    onToggleMode = { viewModel.toggleTechnicalMode() },
                    modifier = Modifier.padding(padding)
                )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnalysisContent(
    analysis: RegionAnalysis,
    isTechnicalMode: Boolean,
    onToggleMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(Modifier.height(8.dp))
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(
                    selected = !isTechnicalMode,
                    onClick = { if (isTechnicalMode) onToggleMode() },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                ) { Text("Visão Geral") }
                SegmentedButton(
                    selected = isTechnicalMode,
                    onClick = { if (!isTechnicalMode) onToggleMode() },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                ) { Text("Modo Técnico") }
            }
            Spacer(Modifier.height(12.dp))
        }

        analysis.weather?.let { weather ->
            item {
                WeatherCard(
                    weather = weather,
                    isTechnicalMode = isTechnicalMode,
                    regionName = "${analysis.regionName}, ${analysis.state}"
                )
                Spacer(Modifier.height(12.dp))
            }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row {
                            Icon(Icons.Filled.WbSunny, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Text("  Previsão 7 Dias", style = MaterialTheme.typography.titleMedium)
                        }
                        Spacer(Modifier.height(8.dp))
                        weather.dailyMaxTemps.take(7).forEachIndexed { i, maxTemp ->
                            val minTemp = weather.dailyMinTemps.getOrNull(i) ?: 0.0
                            val precip = weather.dailyPrecipitation.getOrNull(i) ?: 0.0
                            val dayLabel = listOf("Hoje", "Amanhã", "Ter", "Qua", "Qui", "Sex", "Sáb").getOrNull(i) ?: "Dia ${i + 1}"
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(dayLabel, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                                Text("${maxTemp.toInt()}°/${minTemp.toInt()}°", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                                Text("${precip}mm", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                            }
                            if (i < 6) HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Ranking de Culturas", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = if (isTechnicalMode) "Índices agronômicos com dados técnicos"
                        else "Culturas recomendadas para a região",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(8.dp))
                    analysis.cultureRanking.forEachIndexed { index, culture ->
                        RankingItem(
                            position = index + 1,
                            culture = culture,
                            isTechnicalMode = isTechnicalMode
                        )
                        if (index < analysis.cultureRanking.size - 1) HorizontalDivider()
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Avaliação de Risco", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    RiskRow("Solo", "Adequado para culturas da região")
                    HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                    RiskRow("Hídrico", "Precipitação dentro da média histórica")
                    HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                    RiskRow("Climático", "Temperatura favorável. Sem extremos previstos.")
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun RiskRow(label: String, text: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        Text(text = text, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
