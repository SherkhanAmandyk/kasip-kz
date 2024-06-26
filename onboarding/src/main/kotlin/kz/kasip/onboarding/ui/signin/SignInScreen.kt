package kz.kasip.onboarding.ui.signin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.kasip.designcore.*
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.theme.PrimaryBackgroundGreen

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToMain: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 36.dp)
                .fillMaxWidth()
        ) {
            val isSignedIn by viewModel.isSignedInFlow.collectAsState()
            if (isSignedIn) {
                navigateToMain()
                viewModel.invalidateStates()
            }
            Text(text = lang[email]?:"")
            var email by remember { mutableStateOf(TextFieldValue("")) }
            EmailTextField(text = email) {
                email = it
            }
            Text(text = lang[password]?:"")
            var password by remember { mutableStateOf(TextFieldValue("")) }
            PasswordTextField(text = password) {
                password = it
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.signIn(email.text, password.text)
                },
                colors = ButtonDefaults.buttonColors().copy(containerColor = PrimaryBackgroundGreen)
            ) {
                Text(text = lang[sign_in]?:"")
            }
        }
    }
}