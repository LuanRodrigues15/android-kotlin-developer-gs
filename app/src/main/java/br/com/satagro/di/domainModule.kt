package br.com.satagro.di

import br.com.satagro.domain.usecase.GetCultureRankingUseCase
import br.com.satagro.domain.usecase.GetRegionAnalysisUseCase
import br.com.satagro.domain.usecase.GetWeatherUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetWeatherUseCase(repository = get())
    }

    factory {
        GetRegionAnalysisUseCase(weatherUseCase = get())
    }

    factory {
        GetCultureRankingUseCase()
    }
}
