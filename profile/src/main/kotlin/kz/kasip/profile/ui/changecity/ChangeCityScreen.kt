package kz.kasip.profile.ui.changecity

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

@Composable
fun ChangeCityScreen(
    viewModel: ChangeCityViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = "Change city",
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
                    Text(text = "City")
                    val city by viewModel.textFlow.collectAsState()
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = city,
                        onValueChange = viewModel::onText1Change
                    )
                    Button(onClick = { viewModel.onSave() }) {
                        Text(text = "Save City")
                    }
                }
            }
        }
    }
}
