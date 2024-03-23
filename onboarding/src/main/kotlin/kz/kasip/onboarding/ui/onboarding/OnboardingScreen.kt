package kz.kasip.onboarding.ui.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.kasip.onboarding.R
import kz.kasip.onboarding.navigation.login
import kz.kasip.onboarding.navigation.registration

@Composable
fun OnboardingScreen(navigateTo: (String) -> Unit) {
    Surface {
        Scaffold {
            Box(
                Modifier.padding(it)
                    .padding(horizontal = 10.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    Button(onClick = { navigateTo(registration) }) {
                        Text(text = stringResource(R.string.register_with_email))
                    }
                    TextButton(onClick = { navigateTo(login) }) {
                        Text(text = stringResource(R.string.sign_in))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewOnboardingScreen() {
    OnboardingScreen {  }
}