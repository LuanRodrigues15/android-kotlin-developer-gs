package br.com.satagro.presentation.screens.cultures

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.satagro.presentation.components.CultureCard
import br.com.satagro.presentation.navigation.AppRoutes
import br.com.satagro.presentation.viewmodel.CulturesViewModel
import br.com.satagro.presentation.viewmodel.FavoritesViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CulturesScreen(navController: NavController, favoritesViewModel: FavoritesViewModel) {
    val viewModel: CulturesViewModel = koinViewModel()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredCultures by viewModel.filteredCultures.collectAsState()
    val favoritedCultures by favoritesViewModel.favoritedCultures.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explorar por Cultura") },
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
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Buscar cultura...") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                singleLine = true
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredCultures, key = { it.id }) { culture ->
                    CultureCard(
                        culture = culture,
                        isFavorited = favoritedCultures.any { it.id == culture.id },
                        onClick = { navController.navigate(AppRoutes.detail(culture.id)) },
                        onFavorite = {
                            if (favoritesViewModel.isCultureFavorited(culture.id))
                                favoritesViewModel.removeCulture(culture.id)
                            else
                                favoritesViewModel.addCulture(culture)
                        }
                    )
                }
            }
        }
    }
}
