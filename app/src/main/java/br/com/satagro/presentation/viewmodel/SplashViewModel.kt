package br.com.satagro.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.satagro.data.preferences.PreferenceKeys
import br.com.satagro.data.preferences.dataStore
import br.com.satagro.presentation.navigation.AppRoutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigateTo = MutableStateFlow<String?>(null)
    val navigateTo: StateFlow<String?> = _navigateTo.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            val hasSeenOnboarding = application.dataStore.data
                .map { it[PreferenceKeys.ONBOARDING_COMPLETE] ?: false }
                .first()
            _navigateTo.value = if (hasSeenOnboarding) AppRoutes.HOME else AppRoutes.ONBOARDING
        }
    }
}
