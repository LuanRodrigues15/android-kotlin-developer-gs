package br.com.satagro.presentation.viewmodel

import androidx.lifecycle.ViewModel
import br.com.satagro.data.model.CultureItem
import br.com.satagro.domain.model.RegionAnalysis
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoritesViewModel : ViewModel() {

    private val _favoritedAnalyses = MutableStateFlow<List<RegionAnalysis>>(emptyList())
    val favoritedAnalyses: StateFlow<List<RegionAnalysis>> = _favoritedAnalyses.asStateFlow()

    private val _favoritedCultures = MutableStateFlow<List<CultureItem>>(emptyList())
    val favoritedCultures: StateFlow<List<CultureItem>> = _favoritedCultures.asStateFlow()

    fun addAnalysis(analysis: RegionAnalysis) {
        if (_favoritedAnalyses.value.none { it.regionId == analysis.regionId }) {
            _favoritedAnalyses.value = _favoritedAnalyses.value + analysis
        }
    }

    fun removeAnalysis(regionId: String) {
        _favoritedAnalyses.value = _favoritedAnalyses.value.filter { it.regionId != regionId }
    }

    fun isAnalysisFavorited(regionId: String): Boolean =
        _favoritedAnalyses.value.any { it.regionId == regionId }

    fun addCulture(culture: CultureItem) {
        if (_favoritedCultures.value.none { it.id == culture.id }) {
            _favoritedCultures.value = _favoritedCultures.value + culture
        }
    }

    fun removeCulture(cultureId: String) {
        _favoritedCultures.value = _favoritedCultures.value.filter { it.id != cultureId }
    }

    fun isCultureFavorited(cultureId: String): Boolean =
        _favoritedCultures.value.any { it.id == cultureId }
}
