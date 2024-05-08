package kz.kasip.profile.ui.changename

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
import kz.kasip.designcore.change_name
import kz.kasip.designcore.name
import kz.kasip.designcore.ok
import kz.kasip.designcore.save_name

@Composable
fun ChangeNameScreen(
    viewModel: ChangeNameViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val isNameInvalid by viewModel.isNameInvalidFlow.collectAsState()
    if (isNameInvalid) {
        KasipDialog(
            onDismissRequest = { viewModel.isNameInvalidFlow.update { false } },
            buttons = listOf(ButtonUiState(lang[ok] ?: ""))
        )
    }
    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = lang[change_name] ?: "",
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
                    Text(text = lang[name] ?: "")
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
                        Text(text = lang[save_name] ?: "")
                    }
                }
            }
        }
    }
}
