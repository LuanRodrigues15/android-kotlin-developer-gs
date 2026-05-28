package br.com.satagro.data.model

import br.com.satagro.domain.model.CultureRanking
import br.com.satagro.domain.model.RegionInfo

object RegionData {

    val regions = listOf(
        RegionInfo("sorriso", "Sorriso", "MT", -12.5444, -55.7208),
        RegionInfo("corumba", "Corumbá", "MS", -19.0078, -57.6531),
        RegionInfo("ribeirao_preto", "Ribeirão Preto", "SP", -21.1775, -47.8103)
    )

    fun getById(id: String): RegionInfo = regions.first { it.id == id }

    fun getRanking(regionId: String): List<CultureRanking> = when (regionId) {
        "sorriso" -> listOf(
            CultureRanking(
                cultureId = "soja",
                cultureName = "Soja",
                score = 94,
                simpleSummary = "Solo fértil de cerrado, chuvas regulares de outubro a março.",
                technicalSummary = "Latossolo Vermelho-Amarelo · pH 6,0–6,5 · Precip. 450–800mm · Ciclo 110–125d",
                iconKey = "soja"
            ),
            CultureRanking(
                cultureId = "milho",
                cultureName = "Milho",
                score = 87,
                simpleSummary = "Rotação ideal com soja. Alta produtividade comprovada.",
                technicalSummary = "Temp. 18–32°C · Precip. mín. 500mm · Solo argiloso · Ciclo 100–120d",
                iconKey = "milho"
            ),
            CultureRanking(
                cultureId = "algodao",
                cultureName = "Algodão",
                score = 71,
                simpleSummary = "Viável no período seco. Demanda irrigação complementar.",
                technicalSummary = "Temp. 25–35°C · Período seco 60d · Solo profundo · Fibra media 4,0 UHML",
                iconKey = "algodao"
            )
        )
        "corumba" -> listOf(
            CultureRanking(
                cultureId = "pecuaria",
                cultureName = "Pecuária de corte",
                score = 96,
                simpleSummary = "Planície alagável do Pantanal, pasto nativo abundante.",
                technicalSummary = "Capacidade 0,8–1,2 UA/ha · Pasto nativo · Ciclo 30–36 meses · GPD 0,5kg",
                iconKey = "pecuaria"
            ),
            CultureRanking(
                cultureId = "piscicultura",
                cultureName = "Piscicultura",
                score = 88,
                simpleSummary = "Rios permanentes e temperatura favorável o ano todo.",
                technicalSummary = "Temp. água 22–30°C · OD > 5mg/L · Densidade 2–5 peixe/m² · Ciclo 8–12m",
                iconKey = "piscicultura"
            ),
            CultureRanking(
                cultureId = "arroz",
                cultureName = "Arroz",
                score = 62,
                simpleSummary = "Várzeas permitem cultivo, mas cheias limitam a janela de plantio.",
                technicalSummary = "Várzea inundável · Janela nov–jan · Produt. 4–5 t/ha · Requer drenagem",
                iconKey = "arroz"
            )
        )
        "ribeirao_preto" -> listOf(
            CultureRanking(
                cultureId = "cana",
                cultureName = "Cana-de-açúcar",
                score = 97,
                simpleSummary = "Terra roxa, clima definido e infraestrutura de usinas consolidada.",
                technicalSummary = "Terra Roxa Estruturada · ATR médio 138kg/t · Produt. 85–95 t/ha · TCH>90",
                iconKey = "cana"
            ),
            CultureRanking(
                cultureId = "cafe",
                cultureName = "Café",
                score = 85,
                simpleSummary = "Altitude e temperatura noturna favorecem grãos de qualidade.",
                technicalSummary = "Alt. 600–900m · Temp. noturna 13–17°C · Precip. 1200–1800mm · Bebida mole",
                iconKey = "cafe"
            ),
            CultureRanking(
                cultureId = "soja",
                cultureName = "Soja",
                score = 74,
                simpleSummary = "Viável na entressafra da cana com bom manejo.",
                technicalSummary = "Lat. 21°S · Ciclo precoce 90–95d · Produt. 3,2–3,8 t/ha · Rotação cana",
                iconKey = "soja"
            )
        )
        else -> emptyList()
    }
}
