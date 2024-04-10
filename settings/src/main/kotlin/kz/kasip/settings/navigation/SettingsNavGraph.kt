package kz.kasip.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kz.kasip.settings.ui.changeemail.ChangeEmailScreen
import kz.kasip.settings.ui.changelogin.ChangeLoginScreen
import kz.kasip.settings.ui.changepassword.ChangePasswordScreen
import kz.kasip.settings.ui.changephone.ChangePhoneScreen
import kz.kasip.settings.ui.settings.SettingsScreen
import kz.kasip.settings.ui.settings.settingsScreen

val changeEmailScreen = "changeEmailScreen"
val changeLoginScreen = "changeLoginScreen"
val changePhoneScreen = "changePhoneScreen"
val changePasswordScreen = "changePasswordScreen"

fun NavGraphBuilder.settingsNavGraph(
    navigateToOnboarding: () -> Unit,
    navigateTo: (String) -> Unit,
    onBack: () -> Unit,
) {
    composable(settingsScreen) {
        SettingsScreen(
            navigateToOnboarding = navigateToOnboarding,
            navigateTo = navigateTo,
            onBack = onBack
        )
    }
    composable(changeEmailScreen) {
        ChangeEmailScreen(
            onBack = onBack
        )
    }
    composable(changeLoginScreen) {
        ChangeLoginScreen(
            onBack = onBack
        )
    }
    composable(changePhoneScreen) {
        ChangePhoneScreen(
            onBack = onBack
        )
    }
    composable(changePasswordScreen) {
        ChangePasswordScreen(
            onBack = onBack
        )
    }
}