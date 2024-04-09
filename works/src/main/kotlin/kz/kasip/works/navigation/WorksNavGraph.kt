package kz.kasip.works.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kz.kasip.works.ui.hidden.HiddenWorksScreen
import kz.kasip.works.ui.mywork.MyWorksScreen

val hiddenWorksScreen = "hiddenWorksScreen"
val myWorksScreen = "myWorksScreen"
fun NavGraphBuilder.worksNavGraph(
    onBack: () -> Unit,
) {
    composable(hiddenWorksScreen) {
        HiddenWorksScreen(onBack = onBack)
    }
    composable(myWorksScreen) {
        MyWorksScreen(onBack = onBack)
    }
}