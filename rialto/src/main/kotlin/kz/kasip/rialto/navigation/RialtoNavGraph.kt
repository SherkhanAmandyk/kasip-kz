package kz.kasip.rialto.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import kz.kasip.rialto.RialtoUi
import kz.kasip.rialto.ui.createproject.CreateProjectScreen
import kz.kasip.rialto.ui.filter.SalespersonFilterScreen
import kz.kasip.rialto.ui.main.MainRialtoScreen
import kz.kasip.rialto.ui.main.salesperson.BuyerViewModel
import kz.kasip.rialto.ui.offerservice.OfferAServiceScreen
import kz.kasip.rialto.ui.salesperson.SalespersonViewModel

val rialtoScreen = "rialtoScreen"
val mainRialtoScreen = "mainRialtoScreen"
val salespersonFilterScreen = "salespersonFilterScreen"
val offerAServiceScreen = "offerAServiceScreen"
val createProjectScreen = "createProjectScreen"
val selectSubrubricScreen = "selectSubrubricScreen"

fun NavGraphBuilder.rialtoNavGraph(
    navigateTo: (String) -> Unit,
    onBack: () -> Unit,
) {
    composable(rialtoScreen) {
        val salespersonViewModel: SalespersonViewModel = hiltViewModel()
        val buyerViewModel: BuyerViewModel = hiltViewModel()
        val navController = rememberNavController()
        val navGraph = remember(navController) {
            navController.createGraph(startDestination = mainRialtoScreen) {
                composable(mainRialtoScreen) {
                    MainRialtoScreen(
                        salespersonViewModel = salespersonViewModel,
                        buyerViewModel = buyerViewModel,
                        navigateToFilter = {
                            navController.navigate(salespersonFilterScreen)
                        },
                        navigateToOfferService = {
                            navController.navigate(offerAServiceScreen)
                        },
                        navigateToCreateProject = {
                            navController.navigate(createProjectScreen)
                        },
                        onBack = onBack
                    )
                }
                composable(salespersonFilterScreen) {
                    SalespersonFilterScreen(
                        salespersonViewModel,
                        onBack = { navController.popBackStack() })
                }
                composable(offerAServiceScreen) {
                    OfferAServiceScreen(
                        rialtoUi = salespersonViewModel.selectedRialtoUi ?: RialtoUi.default,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(createProjectScreen) {
                    CreateProjectScreen(
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(selectSubrubricScreen) {

                }
            }
        }
        NavHost(navController = navController, graph = navGraph)
    }
}