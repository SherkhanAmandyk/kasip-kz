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
import kz.kasip.chat.navigation.chatGraph
import kz.kasip.designcore.theme.KasipkzTheme
import kz.kasip.onboarding.navigation.onboarding
import kz.kasip.onboarding.navigation.onboardingGraph
import kz.kasip.order.navigation.orderNavGraph
import kz.kasip.response.ui.MyResponsesScreen
import kz.kasip.rialto.navigation.rialtoNavGraph
import kz.kasip.settings.navigation.settingsNavGraph
import kz.kasip.ui.main.MainScreen
import kz.kasip.works.navigation.worksNavGraph

val myResposesScreen = "myResposesScreen"

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
                            MainScreen { navController.navigate(it) }
                        }
                        settingsNavGraph(
                            navigateToOnboarding = {
                                navController.navigate(route = onboarding) {
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                }
                                navController.clearBackStack(onboarding)
                            },
                            navigateTo = { navController.navigate(it) },
                            onBack = { navController.popBackStack() }
                        )
                        onboardingGraph(
                            navigateTo = { navController.navigate(it) },
                            navigateToMain = {
                                navController.popBackStack()
                                navController.popBackStack()
                                navController.navigate(main)
                            }
                        )
                        rialtoNavGraph(
                            navigateTo = { navController.navigate(it) },
                            onBack = { navController.popBackStack() }
                        )
                        orderNavGraph(
                            navigateTo = { navController.navigate(it) },
                            onBack = { navController.popBackStack() }
                        )
                        chatGraph(
                            onGoToChat = {
                                navController.navigate(
                                    route = "chatScreen/$it"
                                )
                            },
                            onBack = { navController.popBackStack() }
                        )
                        worksNavGraph { navController.popBackStack() }
                        composable(myResposesScreen) {
                            MyResponsesScreen {
                                navController.popBackStack()
                            }
                        }
                    }
                }
                NavHost(navController = navController, graph = navGraph)
            }
        }
    }
}