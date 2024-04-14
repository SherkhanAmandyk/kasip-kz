package kz.kasip.profile.ui.profile

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.update
import kz.kasip.designcore.KasipTopAppBar
import kz.kasip.designcore.theme.Divider
import kz.kasip.designcore.theme.RedBackground
import kz.kasip.profile.R
import kz.kasip.profile.navigation.changeBioScreen
import kz.kasip.profile.navigation.changeCityScreen
import kz.kasip.profile.navigation.changeCountryScreen
import kz.kasip.profile.navigation.changeNameScreen
import kz.kasip.profile.navigation.changeSpecialityScreen
import kz.kasip.designcore.R as DesignR

const val profileScreen = "profileScreen"

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit,
    onBack: () -> Unit,
) {
    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = stringResource(id = R.string.profile),
                    onBack = onBack
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                Column {
                    val profile by viewModel.profileList.collectAsState()

                    profile.forEach {

                        Surface(
                            onClick = {
                                when (it) {
                                    is ProfileInfo.City -> navigateTo(changeCityScreen)
                                    is ProfileInfo.Name -> navigateTo(changeNameScreen)
                                    is ProfileInfo.Bio -> navigateTo(changeBioScreen)
                                    is ProfileInfo.Speciality -> navigateTo(changeSpecialityScreen)
                                    is ProfileInfo.Country -> navigateTo(changeCountryScreen)
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
                            text = "Delete Account",
                        )
                    }
                }
            }
        }
    }
}