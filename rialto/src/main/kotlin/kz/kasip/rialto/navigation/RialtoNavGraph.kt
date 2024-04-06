package kz.kasip.rialto.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kz.kasip.rialto.ui.createproject.CreateProjectScreen
import kz.kasip.rialto.ui.filter.SalespersonFilterScreen
import kz.kasip.rialto.ui.main.MainRialtoScreen
import kz.kasip.rialto.ui.offerservice.OfferAServiceScreen

val mainRialtoScreen = "mainRialtoScreen"
val salespersonFilterScreen = "salespersonFilterScreen"
val offerAServiceScreen = "offerAServiceScreen"
val createProjectScreen = "createProjectScreen"

fun NavGraphBuilder.rialtoNavGraph(
    navigateTo: (String) -> Unit,
    onBack: () -> Unit,
) {
    composable(mainRialtoScreen) {
        MainRialtoScreen(
            navigateToFilter = {
                navigateTo(salespersonFilterScreen)
            },
            navigateToOfferService = {
                navigateTo(offerAServiceScreen)
            },
            navigateToCreateProject = {
                navigateTo(createProjectScreen)
            },
            onBack = onBack
        )
    }
    composable(salespersonFilterScreen) {
        SalespersonFilterScreen(onBack = onBack)
    }
    composable(offerAServiceScreen) {
        OfferAServiceScreen(
            onBack = onBack
        )
    }
    composable(createProjectScreen) {
        CreateProjectScreen(
            onBack = onBack
        )
    }
}