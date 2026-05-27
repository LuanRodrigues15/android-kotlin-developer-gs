package br.com.satagro.di

import br.com.satagro.presentation.viewmodel.AnalysisViewModel
import br.com.satagro.presentation.viewmodel.CulturesViewModel
import br.com.satagro.presentation.viewmodel.FavoritesViewModel
import br.com.satagro.presentation.viewmodel.HomeViewModel
import br.com.satagro.presentation.viewmodel.OnboardingViewModel
import br.com.satagro.presentation.viewmodel.SplashViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::AnalysisViewModel)
    viewModelOf(::CulturesViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::OnboardingViewModel)
}
