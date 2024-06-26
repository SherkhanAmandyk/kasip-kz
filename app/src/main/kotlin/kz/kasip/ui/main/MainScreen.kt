package kz.kasip.ui.main

import android.content.Context.MODE_PRIVATE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kz.kasip.catalog.catalogRubricsScreen
import kz.kasip.catalog.favoriteScreen
import kz.kasip.catalog.viewedScreen
import kz.kasip.chat.navigation.blockListScreen
import kz.kasip.chat.navigation.chatsScreen
import kz.kasip.data.LogOutUseCase
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.MainTopAppBar
import kz.kasip.designcore.block_list
import kz.kasip.designcore.favorite
import kz.kasip.designcore.hidden
import kz.kasip.designcore.log_out
import kz.kasip.designcore.my_response
import kz.kasip.designcore.my_works
import kz.kasip.designcore.notification
import kz.kasip.designcore.photo
import kz.kasip.designcore.profile
import kz.kasip.designcore.rialto
import kz.kasip.designcore.settings
import kz.kasip.designcore.theme.DialogBackground
import kz.kasip.designcore.viewed
import kz.kasip.myResposesScreen
import kz.kasip.notificationsScreen
import kz.kasip.onboarding.navigation.onboarding
import kz.kasip.order.navigation.orderScreen
import kz.kasip.profile.ui.profile.profileScreen
import kz.kasip.rialto.navigation.rialtoScreen
import kz.kasip.settings.ui.settings.settingsScreen
import kz.kasip.works.navigation.hiddenWorksScreen
import kz.kasip.works.navigation.myWorksScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit,
    onSelectPhoto: () -> Unit,
) {
    val isLoggedOut by viewModel.isLoggedOut.collectAsState()
    if (isLoggedOut) {
        navigateTo(onboarding)
    }
    val uiState = MainUiState()

    val onItemClick: (String) -> Unit = {
        when (it) {
            lang[my_response] ?: "" -> navigateTo(myResposesScreen)
            lang[my_works] -> navigateTo(myWorksScreen)
            lang[hidden] ?: "" -> navigateTo(hiddenWorksScreen)
            lang[settings] ?: "" -> navigateTo(settingsScreen)
            lang[profile] ?: "" -> navigateTo(profileScreen)
            lang[rialto] ?: "" -> navigateTo(rialtoScreen)
            lang[block_list] -> navigateTo(blockListScreen)
            lang[favorite] -> navigateTo(favoriteScreen)
            lang[viewed] ?: "" -> navigateTo(viewedScreen)
        }
    }
    val scrollState = rememberScrollState()
    var showBottomSheet by remember { mutableStateOf(false) }


    Surface {
        Scaffold(
            bottomBar = {
                Column {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = DialogBackground,
                        onClick = { viewModel.logOut() }
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .padding(start = 40.dp),
                            text = lang[log_out] ?: "",
                            fontSize = 20.sp,
                            color = Color.Red
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    BottomAppBar(
                        actions = {
                            IconButton(
                                modifier = Modifier.weight(1F),
                                onClick = {
                                    navigateTo(rialtoScreen)
                                },
                                content = {
                                    Icon(
                                        modifier = Modifier.padding(bottom = 8.dp),
                                        painter = painterResource(id = R.drawable.icon_catalog),
                                        contentDescription = "Catalog"
                                    )
                                }
                            )
                            IconButton(
                                modifier = Modifier.weight(1F),
                                onClick = {
                                    navigateTo(chatsScreen)
                                },
                                content = {
                                    Icon(
                                        modifier = Modifier.padding(bottom = 8.dp),
                                        painter = painterResource(id = R.drawable.icon_chats),
                                        contentDescription = "Calendar"
                                    )
                                }
                            )
                            IconButton(
                                modifier = Modifier.weight(1F),
                                onClick = {
                                    navigateTo(orderScreen)
                                },
                                content = {
                                    Icon(
                                        modifier = Modifier.padding(bottom = 8.dp),
                                        painter = painterResource(id = R.drawable.icon_calendar),
                                        contentDescription = "Calendar"
                                    )
                                }
                            )
                            IconButton(
                                modifier = Modifier.weight(1F),
                                onClick = { navigateTo(notificationsScreen) },
                                content = {
                                    Icon(
                                        modifier = Modifier.padding(bottom = 8.dp),
                                        painter = painterResource(id = R.drawable.icon_notification),
                                        contentDescription = lang[notification] ?: ""
                                    )
                                }
                            )
                            IconButton(
                                modifier = Modifier.weight(1F),
                                onClick = {
                                    navigateTo(catalogRubricsScreen)
                                },
                                content = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_works),
                                        contentDescription = "Works"
                                    )
                                }
                            )
                        },
                        contentPadding = PaddingValues(
                            bottom = 14.dp,
                            top = 28.dp
                        )
                    )
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxWidth()
                ) {
                    val user by viewModel.userFlow.collectAsState()
                    val profile by viewModel.profileFlow.collectAsState()
                    val avatar by viewModel.avatarFlow.collectAsState()
                    MainTopAppBar(
                        name = profile?.name ?: user?.email ?: "",
                        profession = profile?.speciality ?: "",
                        avatar = avatar
                    ) {
                        showBottomSheet = true
                    }
                    val textPadding = PaddingValues(top = 24.dp, start = 40.dp, bottom = 12.dp)
                    uiState.sections.forEach {
                        Section(
                            it,
                            textPadding,
                            onItemClick
                        )
                    }
                }
            }
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                tonalElevation = 0.dp,
                scrimColor = Color.Transparent,
                shape = RoundedCornerShape(0.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onSelectPhoto()
                        showBottomSheet = false
                    }
                ) {
                    Text(lang[photo] ?: "")
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun Section(
    section: Pair<String, List<String?>>,
    textPadding: PaddingValues,
    onItemClick: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
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
                    onClick = { onItemClick(it ?: "") }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(textPadding)
                            .fillMaxWidth(),
                        text = it ?: "",
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
            ),
            DataStoreRepository(LocalContext.current.getSharedPreferences("", MODE_PRIVATE))
        ),
        {}
    ) {}
}