package br.com.satagro.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.satagro.domain.common.Resource
import br.com.satagro.domain.model.RegionAnalysis
import br.com.satagro.domain.usecase.GetRegionAnalysisUseCase
import br.com.satagro.presentation.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getRegionAnalysisUseCase: GetRegionAnalysisUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<RegionAnalysis>>(UiState.Initial)
    val uiState: StateFlow<UiState<RegionAnalysis>> = _uiState.asStateFlow()

    private val _selectedRegionId = MutableStateFlow<String?>(null)
    val selectedRegionId: StateFlow<String?> = _selectedRegionId.asStateFlow()

    fun selectRegion(regionId: String) {
        _selectedRegionId.value = regionId
        loadAnalysis(regionId)
    }

    private fun loadAnalysis(regionId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = when (val result = getRegionAnalysisUseCase(regionId)) {
                is Resource.Success -> UiState.Success(result.data)
                is Resource.Error   -> UiState.Error(result.message)
                is Resource.Loading -> UiState.Loading
            }
        }
    }

    fun retry() {
        _selectedRegionId.value?.let { loadAnalysis(it) }
    }
}
