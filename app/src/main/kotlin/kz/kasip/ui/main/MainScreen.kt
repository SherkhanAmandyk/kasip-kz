package kz.kasip.ui.main

import android.content.Context.MODE_PRIVATE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.kasip.R
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.designcore.MainTopAppBar
import kz.kasip.onboarding.navigation.onboarding
import kz.kasip.rialto.navigation.mainRialtoScreen
import kz.kasip.usecase.LogOutUseCase

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit,
) {
    val isLoggedOut by viewModel.isLoggedOut.collectAsState()
    if (isLoggedOut) {
        navigateTo(onboarding)
    }
    val uiState = MainUiState()

    val onItemClick: (String) -> Unit = {
        when (it) {
            "Rialto" -> navigateTo(mainRialtoScreen)
        }
    }
    val scrollState = rememberScrollState()

    Surface {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    actions = {
                        Icon(
                            modifier = Modifier
                                .weight(1F)
                                .padding(bottom = 8.dp),
                            painter = painterResource(id = R.drawable.icon_catalog),
                            contentDescription = stringResource(id = R.string.catalog)
                        )
                        Icon(
                            modifier = Modifier
                                .weight(1F)
                                .padding(bottom = 8.dp),
                            painter = painterResource(id = R.drawable.icon_calendar),
                            contentDescription = stringResource(id = R.string.calendar)
                        )
                        Icon(
                            modifier = Modifier
                                .weight(1F)
                                .padding(bottom = 8.dp),
                            painter = painterResource(id = R.drawable.icon_notification),
                            contentDescription = stringResource(id = R.string.notification)
                        )
                        Icon(
                            modifier = Modifier.weight(1F),
                            painter = painterResource(id = R.drawable.icon_works),
                            contentDescription = stringResource(id = R.string.works)
                        )
                    },
                    contentPadding = PaddingValues(
                        bottom = 14.dp,
                        top = 28.dp
                    )
                )
            }
        ) {
            Box(modifier = Modifier.padding(it).fillMaxWidth()) {
                Column(
                    modifier = Modifier.verticalScroll(scrollState).fillMaxWidth()
                ) {
                    MainTopAppBar()
                    uiState.sections.forEach {
                        Section(
                            it,
                            onItemClick
                        )
                    }
                    TextButton(onClick = {
                        viewModel.logOut()
                    }) {
                        Text(text = "Log Out")
                    }
                }
            }
        }
    }
}

@Composable
fun Section(
    section: Pair<String, List<String>>,
    onItemClick: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val textPadding = PaddingValues(top = 24.dp, start = 40.dp, bottom = 12.dp)
        Text(
            modifier = Modifier.padding(textPadding),
            text = section.first,
            fontSize = 24.sp,
            fontWeight = FontWeight(700)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Color.Black)
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            section.second.forEach {
                Surface(
                    onClick = { onItemClick(it) }
                ) {
                    Text(
                        modifier = Modifier.padding(textPadding).fillMaxWidth(),
                        text = it,
                        fontSize = 21.sp
                    )
                }
            }
        }
    }
}

@Preview(heightDp = 1400)
@Composable
fun PreviewMainScreen() {
    MainScreen(
        MainViewModel(
            LogOutUseCase(
                DataStoreRepository(
                    LocalContext.current.getSharedPreferences(
                        "",
                        MODE_PRIVATE
                    )
                )
            )
        )
    ) {}
}