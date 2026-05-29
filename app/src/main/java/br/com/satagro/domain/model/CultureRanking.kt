package br.com.satagro.domain.model

data class CultureRanking(
    val cultureId: String,
    val cultureName: String,
    val score: Int,
    val simpleSummary: String,
    val technicalSummary: String,
    val iconKey: String
)
