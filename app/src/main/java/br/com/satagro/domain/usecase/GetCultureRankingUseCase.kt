package br.com.satagro.domain.usecase

import br.com.satagro.data.model.CultureData
import br.com.satagro.data.model.RegionData
import br.com.satagro.domain.model.CultureRanking

class GetCultureRankingUseCase {
    operator fun invoke(cultureId: String): List<CultureRanking> =
        RegionData.regions.map { region ->
            val score = CultureData.getRegionalScore(cultureId, region.id)
            CultureRanking(
                cultureId = cultureId,
                cultureName = CultureData.getById(cultureId).name,
                score = score,
                simpleSummary = "Score de ${score} para ${region.name}",
                technicalSummary = "Índice agronômico: $score/100 · Região: ${region.name}, ${region.state}",
                iconKey = cultureId
            )
        }
}
