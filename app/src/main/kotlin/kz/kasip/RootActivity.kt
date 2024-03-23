package kz.kasip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import dagger.hilt.android.AndroidEntryPoint
import kz.kasip.onboarding.navigation.onboardingGraph
import kz.kasip.ui.MainScreen
import kz.kasip.ui.theme.KasipkzTheme

@AndroidEntryPoint
class RootActivity : ComponentActivity() {

    companion object {
        const val main = "main"
    }

    private val viewModel: RootViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KasipkzTheme {
                val navController = rememberNavController()
                val navGraph = remember(navController) {
                    navController.createGraph(startDestination = viewModel.start) {
                        composable(main) {
                            MainScreen()
                        }
                        onboardingGraph(
                            navigateTo = { navController.navigate(it) },
                            navigateToMain = { navController.navigate(main) }
                        )
                    }
                }
                NavHost(navController = navController, graph = navGraph)
            }
        }
    }
}