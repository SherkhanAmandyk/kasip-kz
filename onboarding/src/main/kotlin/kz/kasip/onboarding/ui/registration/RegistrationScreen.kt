package kz.kasip.onboarding.ui.registration

import android.content.Context.MODE_PRIVATE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.data.repository.UserRepository
import kz.kasip.deigncore.EmailTextField
import kz.kasip.deigncore.PasswordTextField
import kz.kasip.onboarding.R
import kz.kasip.onboarding.usecase.RegistrationUseCase

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    navigateToMain: () -> Unit,
) {
    Surface {
        Scaffold {
            val isRegistered by viewModel.isRegisteredFlow.collectAsState()
            if (isRegistered) {
                navigateToMain()
                viewModel.invalidateStates()
            }
            Box(
                Modifier
                    .background(Color.Green)
                    .padding(it)
                    .padding(horizontal = 10.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(top = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    var email by remember { mutableStateOf(TextFieldValue(text = "")) }
                    EmailTextField(email) {
                        email = it
                    }
                    var password by remember { mutableStateOf(TextFieldValue(text = "")) }
                    PasswordTextField(password) {
                        password = it
                    }
                    var repeatPassword by remember { mutableStateOf(TextFieldValue(text = "")) }
                    PasswordTextField(repeatPassword) {
                        repeatPassword = it
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp, horizontal = 88.dp),
                        onClick = {
                            viewModel.onRegister(
                                email.text,
                                password.text,
                                repeatPassword.text
                            )
                        }
                    ) {
                        Text(text = stringResource(id = R.string.register))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewRegistrationScreen() {
    RegistrationScreen(
        RegistrationViewModel(
            RegistrationUseCase(
                UserRepository(),
                DataStoreRepository(LocalContext.current.getSharedPreferences("", MODE_PRIVATE))
            )
        )
    ) {}
}