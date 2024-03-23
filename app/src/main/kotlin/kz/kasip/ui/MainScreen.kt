package kz.kasip.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.kasip.R
import kz.kasip.deigncore.MainTopAppBar

@Composable
fun MainScreen() {
    Surface {
        Scaffold(
            topBar = {
                MainTopAppBar()
            },
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
            Box(modifier = Modifier.padding(it)) {

            }
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen()
}