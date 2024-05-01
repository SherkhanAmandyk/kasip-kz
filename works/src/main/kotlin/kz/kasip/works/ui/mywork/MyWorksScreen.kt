package kz.kasip.works.ui.mywork

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.kasip.designcore.*
import kz.kasip.designcore.Lang.lang
import kz.kasip.works.ui.WorkCard

@Composable
fun MyWorksScreen(
    viewModel: MyWorksViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = lang[my_works]?:"",
                    onBack = onBack
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier.verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val works by viewModel.works.collectAsState()
                    works.forEach {
                        WorkCard(it)
                    }
                }
            }
        }
    }
}