package kz.kasip.settings.ui.changepassword

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.kasip.designcore.KasipTopAppBar
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.PasswordTextField
import kz.kasip.designcore.change_password
import kz.kasip.designcore.password
import kz.kasip.designcore.repeat_password
import kz.kasip.designcore.save_password

@Composable
fun ChangePasswordScreen(
    viewModel: ChangePasswordViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = lang[change_password]?:"",
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
                    Text(text = lang[password] ?: "")
                    val password by viewModel.text1Flow.collectAsState()
                    PasswordTextField(password, viewModel::onText1Change)
                    Text(
                        text = lang[repeat_password] ?: ""
                    )
                    val repeatPassword by viewModel.text2Flow.collectAsState()
                    PasswordTextField(repeatPassword, viewModel::onText2Change)
                    Button(onClick = {
                        viewModel.onSave()
                        onBack()
                    }) {
                        Text(text = lang[save_password] ?: "")
                    }
                }
            }
        }
    }
}
