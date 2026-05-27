package br.com.satagro.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.satagro.data.model.CultureData
import br.com.satagro.data.model.CultureItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class CulturesViewModel : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _allCultures = MutableStateFlow(CultureData.all)

    val filteredCultures: StateFlow<List<CultureItem>> = combine(_searchQuery, _allCultures) { query, cultures ->
        if (query.isBlank()) cultures
        else cultures.filter { it.name.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CultureData.all)

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}
