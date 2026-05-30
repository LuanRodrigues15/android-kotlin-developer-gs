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

class AnalysisViewModel(
    private val getRegionAnalysisUseCase: GetRegionAnalysisUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<RegionAnalysis>>(UiState.Loading)
    val uiState: StateFlow<UiState<RegionAnalysis>> = _uiState.asStateFlow()

    private val _isTechnicalMode = MutableStateFlow(false)
    val isTechnicalMode: StateFlow<Boolean> = _isTechnicalMode.asStateFlow()

    private var currentRegionId: String? = null

    fun loadAnalysis(regionId: String) {
        currentRegionId = regionId
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = when (val result = getRegionAnalysisUseCase(regionId)) {
                is Resource.Success -> UiState.Success(result.data)
                is Resource.Error   -> UiState.Error(result.message)
                is Resource.Loading -> UiState.Loading
            }
        }
    }

    fun retry() { currentRegionId?.let { loadAnalysis(it) } }

    fun toggleTechnicalMode() { _isTechnicalMode.value = !_isTechnicalMode.value }
}
