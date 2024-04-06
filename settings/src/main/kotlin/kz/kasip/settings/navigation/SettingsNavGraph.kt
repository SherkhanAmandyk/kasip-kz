package kz.kasip.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kz.kasip.settings.ui.settings.SettingsScreen
import kz.kasip.settings.ui.settings.settingsScreen

fun NavGraphBuilder.settingsNavGraph(
    navigateTo: (String) -> Unit,
    onBack: () -> Unit,
) {
    composable(settingsScreen) {
        SettingsScreen(
            navigateTo = navigateTo,
            onBack = onBack
        )
    }
}