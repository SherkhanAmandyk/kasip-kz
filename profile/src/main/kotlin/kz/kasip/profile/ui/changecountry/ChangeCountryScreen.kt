package kz.kasip.profile.ui.changebio

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.kasip.designcore.KasipTopAppBar
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.country

@Composable
fun ChangeCountryScreen(
    viewModel: ChangeCountryViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = "Change bio",
                    onBack = onBack
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 46.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = lang[country]?:"")
                    val text by viewModel.textFlow.collectAsState()
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = text,
                        onValueChange = viewModel::onTextChange
                    )
                    Button(onClick = { viewModel.onSave() }) {
                        Text(text = "Save Bio")
                    }
                }
            }
        }
    }
}
