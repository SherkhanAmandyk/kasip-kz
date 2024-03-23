package kz.kasip.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kz.kasip.onboarding.ui.onboarding.OnboardingScreen
import kz.kasip.onboarding.ui.registration.RegistrationScreen
import kz.kasip.onboarding.ui.signin.SignInScreen

val onboarding = "onboarding"
val login = "onboarding/login"
val registration = "onboarding/registration"

fun NavGraphBuilder.onboardingGraph(
    navigateToMain: () -> Unit,
    navigateTo: (String) -> Unit,
) {
    composable(onboarding) {
        OnboardingScreen(navigateTo = navigateTo)
    }
    composable(login) {
        SignInScreen(navigateToMain = navigateToMain)
    }
    composable(registration) {
        RegistrationScreen(navigateToMain = navigateToMain)
    }
}