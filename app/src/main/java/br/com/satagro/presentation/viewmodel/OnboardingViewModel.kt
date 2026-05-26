package br.com.satagro.presentation.viewmodel

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.satagro.data.preferences.PreferenceKeys
import br.com.satagro.data.preferences.dataStore
import kotlinx.coroutines.launch

class OnboardingViewModel(application: Application) : AndroidViewModel(application) {

    fun completeOnboarding(onComplete: () -> Unit) {
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[PreferenceKeys.ONBOARDING_COMPLETE] = true
            }
            onComplete()
        }
    }
}
