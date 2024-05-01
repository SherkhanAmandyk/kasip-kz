package kz.kasip.rialto.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.kasip.designcore.*
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.theme.DialogBackground
import kz.kasip.rialto.R
import kz.kasip.rialto.ui.main.salesperson.BuyerTab
import kz.kasip.rialto.ui.main.salesperson.BuyerViewModel
import kz.kasip.rialto.ui.salesperson.SalespersonTab
import kz.kasip.rialto.ui.salesperson.SalespersonViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainRialtoScreen(
    viewModel: MainRialtoViewModel = hiltViewModel(),
    salespersonViewModel: SalespersonViewModel = hiltViewModel(),
    buyerViewModel: BuyerViewModel = hiltViewModel(),
    navigateToFilter: () -> Unit,
    navigateToOfferService: () -> Unit,
    navigateToCreateProject: () -> Unit,
    onBack: () -> Unit,
) {
    val selectedTabIndex by viewModel.selectedTabIndexFlow.collectAsState()
    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = lang[rialto]?:"",
                    onBack = onBack
                ) {
                    if (selectedTabIndex == 0) {
                        IconButton(onClick = { navigateToFilter() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.filter),
                                contentDescription = ""
                            )
                        }
                    } else {
                        TextButton(onClick = navigateToCreateProject) {
                            Text(text = lang[create] ?: "", color = Color.Black)
                        }
                    }
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
            ) {
                Column {
                    Tabs(selectedTabIndex, viewModel::onSelectedTabChange)
                    val pagerState = rememberPagerState {
                        2
                    }
                    LaunchedEffect(selectedTabIndex) {
                        if (pagerState.currentPage != selectedTabIndex) {
                            pagerState.scrollToPage(selectedTabIndex)
                        }
                    }
                    LaunchedEffect(pagerState) {
                        snapshotFlow { pagerState.currentPage }.collect { page ->
                            viewModel.onSelectedTabChange(page)
                        }
                    }
                    HorizontalPager(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.Top,
                        state = pagerState
                    ) {
                        when (it) {
                            0 -> {
                                SalespersonTab(
                                    viewModel = salespersonViewModel,
                                    navigateToOfferService = {
                                        salespersonViewModel.selectedRialtoUi = it
                                        navigateToOfferService()
                                    }
                                )
                            }

                            1 -> {
                                BuyerTab(
                                    navigateToCreateProject = navigateToCreateProject
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Tabs(selectedTabIndex: Int, onTabChange: (Int) -> Unit) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = Color.Black,
        containerColor = DialogBackground
    ) {
        Tab(
            modifier = Modifier.padding(vertical = 12.dp),
            selected = true,
            onClick = { onTabChange(0) }) {
            Text(
                text = lang[i_am_a_salesperson]?:"",
                fontWeight = FontWeight(700)
            )
        }
        Tab(
            modifier = Modifier.padding(vertical = 12.dp),
            selected = true,
            onClick = { onTabChange(1) }
        ) {
            Text(
                text = lang[i_am_a_buyer]?:"",
                fontWeight = FontWeight(700)
            )
        }
    }
}

@Preview
@Composable
fun PreviewMainRialtoScreen() {
    MainRialtoScreen(
        viewModel = MainRialtoViewModel(),
        navigateToFilter = {},
        navigateToOfferService = {},
        navigateToCreateProject = {},
        onBack = {}
    )
}