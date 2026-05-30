package br.com.satagro.presentation.navigation

object AppRoutes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val HOME = "home"
    const val ANALYSIS = "analysis/{regionId}"
    const val CULTURES = "cultures"
    const val DETAIL = "detail/{cultureId}"
    const val FAVORITES = "favorites"

    fun analysis(regionId: String) = "analysis/$regionId"
    fun detail(cultureId: String) = "detail/$cultureId"
}
