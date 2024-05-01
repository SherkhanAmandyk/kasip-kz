package kz.kasip.settings.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.update
import kz.kasip.designcore.ButtonUiState
import kz.kasip.designcore.KasipDialog
import kz.kasip.designcore.KasipTopAppBar
import kz.kasip.designcore.app_language
import kz.kasip.designcore.delete_account
import kz.kasip.designcore.english
import kz.kasip.designcore.no
import kz.kasip.designcore.settings
import kz.kasip.designcore.theme.Divider
import kz.kasip.designcore.theme.GB
import kz.kasip.designcore.theme.RedBackground
import kz.kasip.designcore.yes
import kz.kasip.settings.navigation.changeEmailScreen
import kz.kasip.settings.navigation.changeLoginScreen
import kz.kasip.settings.navigation.changePasswordScreen
import kz.kasip.settings.navigation.changePhoneScreen
import java.util.Locale
import kz.kasip.designcore.R as DesignR

const val settingsScreen = "settingsScreen"

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateToOnboarding: () -> Unit,
    navigateTo: (String) -> Unit,
    onBack: () -> Unit,
) {
    val appLang by viewModel.appLangFlow.collectAsState()
    val showDialog by viewModel.showDeleteDialog.collectAsState()
    if (showDialog) {
        KasipDialog(
            title = "Do you want to delete your account?",
            onDismissRequest = { viewModel.showDeleteDialog.update { false } },
            buttons = listOf(
                ButtonUiState(appLang[yes] ?: "", color = GB),
                ButtonUiState(appLang[no] ?: "", color = GB)
            )
        ) {
            if (it.text == appLang[yes] ?: "") {
                viewModel.deleteAccount()
            }
        }
    }

    val isDeleted by viewModel.isDeleted.collectAsState()
    if (isDeleted) {
        navigateToOnboarding()
        viewModel.isDeleted.update { false }
    }

    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = appLang[settings] ?: "",
                    onBack = onBack
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                Column {
                    val settings by viewModel.settingsList.collectAsState()

                    settings.forEach {
                        Surface(
                            onClick = {
                                when (it) {
                                    is Setting.Password -> navigateTo(changePasswordScreen)
                                    is Setting.Email -> navigateTo(changeEmailScreen)
                                    is Setting.Login -> navigateTo(changeLoginScreen)
                                    is Setting.Phone -> navigateTo(changePhoneScreen)
                                }
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 14.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = appLang[it.name.lowercase(Locale.ROOT)
                                            .replace(" ", "_")] ?: it.name,
                                        fontSize = 22.sp
                                    )
                                    Row(
                                        modifier = Modifier.widthIn(min = 150.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = it.value,
                                            fontSize = 17.sp
                                        )
                                        Icon(
                                            painter = painterResource(id = DesignR.drawable.icon_forward),
                                            contentDescription = "Go to ${it.name} ${it.value}"
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(color = Divider),
                                )
                            }
                        }
                    }
                    Surface(
                        onClick = {
                            viewModel.changeLang()
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = appLang[app_language] ?: "",
                                    fontSize = 22.sp
                                )
                                Row(
                                    modifier = Modifier.widthIn(min = 150.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = appLang[english] ?: "",
                                        fontSize = 17.sp
                                    )
                                    Icon(
                                        painter = painterResource(id = DesignR.drawable.icon_forward),
                                        contentDescription = ""
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(color = Divider),
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(54.dp))
                    Surface(
                        color = RedBackground,
                        onClick = {
                            viewModel.showDeleteDialog.update { true }
                        }
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            fontSize = 22.sp,
                            text = appLang[delete_account] ?: "",
                        )
                    }
                }
            }
        }
    }
}