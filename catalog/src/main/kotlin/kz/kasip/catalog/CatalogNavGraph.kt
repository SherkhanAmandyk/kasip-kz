package kz.kasip.catalog

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kz.kasip.catalog.favorite.FavoriteScreen
import kz.kasip.catalog.items.CatalogScreen
import kz.kasip.catalog.rubrics.CatalogRubricsScreen
import kz.kasip.catalog.viewed.ViewedScreen

val catalogRubricsScreen = "catalogRubricsScreen"
val catalogScreen = "catalogScreen/{rubricId}"
val favoriteScreen = "favoriteScreen"
val viewedScreen = "viewedScreen"

fun NavGraphBuilder.catalogNavGraph(
    navigateToCatalog: (String) -> Unit,
    onBack: () -> Unit,
) {
    composable(catalogRubricsScreen) {
        CatalogRubricsScreen(
            navigateToCatalog = navigateToCatalog,
            onBack = onBack
        )
    }
    composable(
        route = catalogScreen,
        arguments = listOf(navArgument("rubricId") { type = NavType.StringType })
    ) {
        CatalogScreen(
            it.arguments?.getString("rubricId", "") ?: "",
            onBack = onBack
        )
    }
    composable(favoriteScreen) {
        FavoriteScreen(onBack = onBack)
    }
    composable(viewedScreen) {
        ViewedScreen(onBack = onBack)
    }
}