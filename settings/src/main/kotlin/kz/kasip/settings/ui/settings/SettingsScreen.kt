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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.kasip.designcore.KasipTopAppBar
import kz.kasip.designcore.theme.Divider
import kz.kasip.designcore.theme.RedBackground
import kz.kasip.settings.R
import kz.kasip.designcore.R as DesignR

const val settingsScreen = "settingsScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit,
    onBack: () -> Unit,
) {
    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = stringResource(id = R.string.settings),
                    onBack = onBack
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                Column {
                    val settings by viewModel.settingsList.collectAsState()

                    settings.forEach {
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
                                    text = it.name,
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
                    Spacer(modifier = Modifier.height(54.dp))
                    Surface(
                        color = RedBackground,
                        onClick = {
                            navigateTo("deleteAccount")
                        }
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            fontSize = 22.sp,
                            text = "Delete Account",
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen(
        viewModel = SettingsViewModel(),
        navigateTo = {},
        onBack = {}
    )
}