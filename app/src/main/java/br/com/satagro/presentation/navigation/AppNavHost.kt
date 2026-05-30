package br.com.satagro.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.satagro.presentation.screens.analysis.AnalysisScreen
import br.com.satagro.presentation.screens.cultures.CulturesScreen
import br.com.satagro.presentation.screens.detail.DetailScreen
import br.com.satagro.presentation.screens.favorites.FavoritesScreen
import br.com.satagro.presentation.screens.home.HomeScreen
import br.com.satagro.presentation.screens.onboarding.OnboardingScreen
import br.com.satagro.presentation.screens.splash.SplashScreen
import br.com.satagro.presentation.viewmodel.FavoritesViewModel

@Composable
fun AppNavHost(
    favoritesViewModel: FavoritesViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH
    ) {
        composable(AppRoutes.SPLASH) {
            SplashScreen(navController = navController)
        }

        composable(AppRoutes.ONBOARDING) {
            OnboardingScreen(navController = navController)
        }

        composable(AppRoutes.HOME) {
            HomeScreen(navController = navController)
        }

        composable(
            route = AppRoutes.ANALYSIS,
            arguments = listOf(navArgument("regionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val regionId = backStackEntry.arguments?.getString("regionId") ?: ""
            AnalysisScreen(
                regionId = regionId,
                navController = navController,
                favoritesViewModel = favoritesViewModel
            )
        }

        composable(AppRoutes.CULTURES) {
            CulturesScreen(
                navController = navController,
                favoritesViewModel = favoritesViewModel
            )
        }

        composable(
            route = AppRoutes.DETAIL,
            arguments = listOf(navArgument("cultureId") { type = NavType.StringType })
        ) { backStackEntry ->
            val cultureId = backStackEntry.arguments?.getString("cultureId") ?: ""
            DetailScreen(
                cultureId = cultureId,
                navController = navController,
                favoritesViewModel = favoritesViewModel
            )
        }

        composable(AppRoutes.FAVORITES) {
            FavoritesScreen(
                navController = navController,
                favoritesViewModel = favoritesViewModel
            )
        }
    }
}
