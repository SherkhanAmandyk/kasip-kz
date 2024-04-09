package kz.kasip.order.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import kz.kasip.order.OrderUi
import kz.kasip.order.ui.createproject.CreateProjectScreen
import kz.kasip.order.ui.filter.SalespersonFilterScreen
import kz.kasip.order.ui.main.MainOrderScreen
import kz.kasip.order.ui.main.salesperson.BuyerViewModel
import kz.kasip.order.ui.offerservice.OfferAServiceScreen
import kz.kasip.order.ui.salesperson.SalespersonViewModel

val orderScreen = "orderScreen"
val mainOrderScreen = "mainOrderScreen"
val salespersonFilterScreen = "salespersonFilterScreen"
val offerAServiceScreen = "offerAServiceScreen"
val createProjectScreen = "createProjectScreen"
val selectSubrubricScreen = "selectSubrubricScreen"

fun NavGraphBuilder.orderNavGraph(
    navigateTo: (String) -> Unit,
    onBack: () -> Unit,
) {
    composable(orderScreen) {
        val salespersonViewModel: SalespersonViewModel = hiltViewModel()
        val buyerViewModel: BuyerViewModel = hiltViewModel()
        val navController = rememberNavController()
        val navGraph = remember(navController) {
            navController.createGraph(startDestination = mainOrderScreen) {
                composable(mainOrderScreen) {
                    MainOrderScreen(
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
                        orderUi = salespersonViewModel.selectedOrderUi ?: OrderUi.default,
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