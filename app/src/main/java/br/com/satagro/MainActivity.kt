package br.com.satagro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import br.com.satagro.presentation.navigation.AppNavHost
import br.com.satagro.presentation.viewmodel.FavoritesViewModel
import br.com.satagro.ui.theme.SatagroTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val favoritesViewModel: FavoritesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SatagroTheme {
                AppNavHost(favoritesViewModel = favoritesViewModel)
            }
        }
    }
}
