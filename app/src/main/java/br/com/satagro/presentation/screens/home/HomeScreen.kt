package br.com.satagro.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Satellite
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.satagro.data.model.RegionData
import br.com.satagro.presentation.common.UiState
import br.com.satagro.presentation.components.ErrorMessage
import br.com.satagro.presentation.components.LoadingIndicator
import br.com.satagro.presentation.components.RegionSelector
import br.com.satagro.presentation.components.WeatherCard
import br.com.satagro.presentation.navigation.AppRoutes
import br.com.satagro.presentation.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val selectedRegionId by viewModel.selectedRegionId.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("AgroSat", style = MaterialTheme.typography.titleLarge)
                        Text("Consultoria Agrícola por Satélite", style = MaterialTheme.typography.labelSmall)
                    }
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.Satellite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate(AppRoutes.FAVORITES) }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Favoritos")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Selecione uma região:",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(8.dp))
            RegionSelector(
                regions = RegionData.regions,
                selectedId = selectedRegionId,
                onSelect = { viewModel.selectRegion(it) }
            )
            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                when (val state = uiState) {
                    is UiState.Initial -> {
                        Text(
                            text = "Selecione uma região para começar",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(32.dp)
                        )
                    }
                    is UiState.Loading -> LoadingIndicator()
                    is UiState.Success -> {
                        Column(modifier = Modifier.padding(16.dp)) {
                            WeatherCard(
                                weather = state.data.weather!!,
                                isTechnicalMode = false,
                                regionName = "${state.data.regionName}, ${state.data.state}"
                            )
                            Spacer(Modifier.height(16.dp))
                            Button(
                                onClick = { navController.navigate(AppRoutes.analysis(state.data.regionId)) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Ver Análise Completa")
                            }
                            Spacer(Modifier.height(8.dp))
                            OutlinedButton(
                                onClick = { navController.navigate(AppRoutes.CULTURES) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Explorar por Cultura")
                            }
                        }
                    }
                    is UiState.Error -> {
                        ErrorMessage(
                            message = state.message,
                            onRetry = { viewModel.retry() }
                        )
                    }
                }
            }

            if (uiState is UiState.Initial) {
                OutlinedButton(
                    onClick = { navController.navigate(AppRoutes.CULTURES) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Explorar Culturas")
                }
            }
        }
    }
}
