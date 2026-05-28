package br.com.satagro.data.model

data class CultureItem(
    val id: String,
    val name: String,
    val badge: String,
    val iconKey: String,
    val idealTemperature: String,
    val idealRainfall: String,
    val idealSoil: String,
    val description: String
)

object CultureData {

    val all = listOf(
        CultureItem("soja", "Soja", "Alta demanda", "soja", "20–30°C", "450–800mm", "Latossolo", "Principal cultura de exportação brasileira, com grande versatilidade de cultivo no cerrado."),
        CultureItem("milho", "Milho", "Alta demanda", "milho", "18–32°C", "500–800mm", "Latossolo", "Cultivado em todo o Brasil, essencial para alimentação humana e animal."),
        CultureItem("cana", "Cana-de-açúcar", "Alta demanda", "cana", "21–35°C", "1200–1500mm", "Terra Roxa", "Base da indústria sucroalcooleira nacional, com longa tradição em SP."),
        CultureItem("arroz", "Arroz", "Versátil", "arroz", "20–35°C", "900–1200mm", "Argissolo", "Alimento básico da população brasileira, cultivado em várzeas e terras altas."),
        CultureItem("algodao", "Algodão", "Versátil", "algodao", "25–35°C", "700–1200mm", "Latossolo", "Fibra de alta qualidade, exportada mundialmente. Cultivado principalmente no MT."),
        CultureItem("cafe", "Café", "Alta demanda", "cafe", "18–26°C", "1200–1800mm", "Latossolo Roxo", "Reconhecido mundialmente por qualidade e variedade. Destaque em MG, SP e ES."),
        CultureItem("mandioca", "Mandioca", "Versátil", "mandioca", "18–35°C", "500–1000mm", "Arenoso", "Altamente adaptável a diferentes solos e climas. Base alimentar em várias regiões."),
        CultureItem("pecuaria", "Pecuária de corte", "Alta demanda", "pecuaria", "15–35°C", "600–1200mm", "Pastagens", "Aproveita vastas pastagens nativas e cultivadas. Destaque no Pantanal e Cerrado."),
        CultureItem("piscicultura", "Piscicultura", "Versátil", "piscicultura", "22–30°C", "800–1500mm", "Várzeas", "Explora rios perenes e açudes para produção de tilápia, tambaqui e pacu."),
        CultureItem("frango", "Frango de corte", "Alta demanda", "frango", "20–28°C", "800–1200mm", "Qualquer", "Avicultura altamente tecnificada. Brasil é um dos maiores exportadores mundiais.")
    )

    private val regionalScores = mapOf(
        "soja"        to mapOf("sorriso" to 94, "corumba" to 42, "ribeirao_preto" to 74),
        "milho"       to mapOf("sorriso" to 87, "corumba" to 48, "ribeirao_preto" to 68),
        "cana"        to mapOf("sorriso" to 52, "corumba" to 35, "ribeirao_preto" to 97),
        "arroz"       to mapOf("sorriso" to 58, "corumba" to 62, "ribeirao_preto" to 48),
        "algodao"     to mapOf("sorriso" to 71, "corumba" to 38, "ribeirao_preto" to 55),
        "cafe"        to mapOf("sorriso" to 45, "corumba" to 28, "ribeirao_preto" to 85),
        "mandioca"    to mapOf("sorriso" to 62, "corumba" to 55, "ribeirao_preto" to 60),
        "pecuaria"    to mapOf("sorriso" to 65, "corumba" to 96, "ribeirao_preto" to 50),
        "piscicultura" to mapOf("sorriso" to 48, "corumba" to 88, "ribeirao_preto" to 40),
        "frango"      to mapOf("sorriso" to 70, "corumba" to 60, "ribeirao_preto" to 75)
    )

    fun getById(id: String): CultureItem = all.first { it.id == id }

    fun getRegionalScore(cultureId: String, regionId: String): Int =
        regionalScores[cultureId]?.get(regionId) ?: 50
}
