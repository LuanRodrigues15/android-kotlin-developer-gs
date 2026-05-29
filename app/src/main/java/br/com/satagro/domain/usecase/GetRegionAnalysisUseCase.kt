package br.com.satagro.domain.usecase

import br.com.satagro.data.model.RegionData
import br.com.satagro.domain.common.Resource
import br.com.satagro.domain.model.RegionAnalysis

class GetRegionAnalysisUseCase(private val weatherUseCase: GetWeatherUseCase) {
    suspend operator fun invoke(regionId: String): Resource<RegionAnalysis> {
        val region = RegionData.getById(regionId)
        return when (val weatherResult = weatherUseCase(region.latitude, region.longitude)) {
            is Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(
                RegionAnalysis(
                    regionId = region.id,
                    regionName = region.name,
                    state = region.state,
                    latitude = region.latitude,
                    longitude = region.longitude,
                    weather = weatherResult.data,
                    cultureRanking = RegionData.getRanking(regionId)
                )
            )
            is Resource.Error -> Resource.Error(weatherResult.message)
        }
    }
}
