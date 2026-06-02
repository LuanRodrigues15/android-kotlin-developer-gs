package br.com.satagro.presentation.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.satagro.data.model.CultureItem
import br.com.satagro.domain.model.RegionAnalysis
import br.com.satagro.presentation.components.getCultureIcon
import br.com.satagro.presentation.navigation.AppRoutes
import br.com.satagro.presentation.viewmodel.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController, favoritesViewModel: FavoritesViewModel) {
    val favoritedAnalyses by favoritesViewModel.favoritedAnalyses.collectAsState()
    val favoritedCultures by favoritesViewModel.favoritedCultures.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favoritos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Análises (${favoritedAnalyses.size})") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Culturas (${favoritedCultures.size})") }
                )
            }

            when (selectedTab) {
                0 -> AnalysesList(
                    analyses = favoritedAnalyses,
                    onItemClick = { navController.navigate(AppRoutes.analysis(it.regionId)) },
                    onRemove = { favoritesViewModel.removeAnalysis(it.regionId) }
                )
                1 -> CulturesList(
                    cultures = favoritedCultures,
                    onItemClick = { navController.navigate(AppRoutes.detail(it.id)) },
                    onRemove = { favoritesViewModel.removeCulture(it.id) }
                )
            }
        }
    }
}

@Composable
private fun AnalysesList(
    analyses: List<RegionAnalysis>,
    onItemClick: (RegionAnalysis) -> Unit,
    onRemove: (RegionAnalysis) -> Unit
) {
    if (analyses.isEmpty()) {
        EmptyFavorites("Nenhuma análise salva ainda.\nAcesse uma região e toque no coração.")
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(analyses, key = { it.regionId }) { analysis ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    onClick = { onItemClick(analysis) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("${analysis.regionName}, ${analysis.state}", style = MaterialTheme.typography.titleSmall)
                            analysis.weather?.let {
                                Text(
                                    "${it.temperatureCurrent}°C · ${it.precipitation}mm",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        IconButton(onClick = { onRemove(analysis) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Remover", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CulturesList(
    cultures: List<CultureItem>,
    onItemClick: (CultureItem) -> Unit,
    onRemove: (CultureItem) -> Unit
) {
    if (cultures.isEmpty()) {
        EmptyFavorites("Nenhuma cultura salva ainda.\nExplore culturas e toque no coração.")
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(cultures, key = { it.id }) { culture ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    onClick = { onItemClick(culture) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                getCultureIcon(culture.iconKey),
                                contentDescription = null,
                                modifier = Modifier.size(28.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Column(modifier = Modifier.padding(start = 12.dp)) {
                                Text(culture.name, style = MaterialTheme.typography.titleSmall)
                                Text(culture.badge, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        IconButton(onClick = { onRemove(culture) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Remover", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyFavorites(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Filled.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
