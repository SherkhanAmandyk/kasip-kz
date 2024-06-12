package kz.kasip.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kz.kasip.profile.ui.changebio.ChangeBioScreen
import kz.kasip.profile.ui.changebio.ChangeCountryScreen
import kz.kasip.profile.ui.changecity.ChangeCityScreen
import kz.kasip.profile.ui.changename.ChangeNameScreen
import kz.kasip.profile.ui.changespeciality.ChangeSpecialityScreen
import kz.kasip.profile.ui.profile.ProfileScreen
import kz.kasip.profile.ui.profile.profileScreen

val changeNameScreen = "changeNameScreen"
val changeBioScreen = "changeBioScreen"
val changeSpecialityScreen = "changeSpecialityScreen"
val changeCityScreen = "changeCityScreen"
val changeCountryScreen = "changeCountryScreen"

fun NavGraphBuilder.profileNavGraph(
    navigateTo: (String) -> Unit,
    onBack: () -> Unit,
) {
    composable(profileScreen) {
        ProfileScreen(
            navigateTo = navigateTo,
            onBack = onBack
        )
    }
    composable(changeNameScreen) {
        ChangeNameScreen(
            onBack = onBack
        )
    }
    composable(changeBioScreen) {
        ChangeBioScreen(
            onBack = onBack
        )
    }
    composable(changeSpecialityScreen) {
        ChangeSpecialityScreen(
            onBack = onBack
        )
    }
    composable(changeCityScreen) {
        ChangeCityScreen(
            onBack = onBack
        )
    }
    composable(changeCountryScreen) {
        ChangeCountryScreen(onBack = onBack)
    }
}