package kz.kasip.profile.ui.changespeciality

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
import kotlinx.coroutines.flow.update
import kz.kasip.designcore.ButtonUiState
import kz.kasip.designcore.KasipDialog
import kz.kasip.designcore.KasipTopAppBar
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.ok
import kz.kasip.designcore.save_speciality
import kz.kasip.designcore.speciality

@Composable
fun ChangeSpecialityScreen(
    viewModel: ChangeSpecialityViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val isSpecialityInvalid by viewModel.isSpecialityInvalidFlow.collectAsState()
    if (isSpecialityInvalid) {
        KasipDialog(
            onDismissRequest = { viewModel.isSpecialityInvalidFlow.update { false } },
            buttons = listOf(ButtonUiState(lang[ok] ?: ""))
        )
    }
    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = "Change speciality",
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
                    Text(text = lang[speciality] ?: "")
                    val text by viewModel.textFlow.collectAsState()
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = text,
                        onValueChange = viewModel::onTextChange
                    )
                    Button(onClick = {
                        viewModel.onSave()
                        onBack()
                    }) {
                        Text(lang[save_speciality] ?: "")
                    }
                }
            }
        }
    }
}
