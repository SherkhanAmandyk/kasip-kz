package kz.kasip.works.ui.hidden

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.kasip.designcore.KasipTopAppBar
import kz.kasip.designcore.theme.CardBackground
import kz.kasip.works.R
import kz.kasip.works.ui.WorkCard

@Composable
fun HiddenWorksScreen(
    viewModel: HiddenWorksViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = stringResource(id = R.string.hidden_works),
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