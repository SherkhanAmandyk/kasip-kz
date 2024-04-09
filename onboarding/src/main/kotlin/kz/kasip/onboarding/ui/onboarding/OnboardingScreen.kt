package kz.kasip.onboarding.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.kasip.designcore.theme.PrimaryBackgroundGreen
import kz.kasip.onboarding.R
import kz.kasip.onboarding.navigation.login
import kz.kasip.onboarding.navigation.registration

@Composable
fun OnboardingScreen(navigateTo: (String) -> Unit) {
    Surface(
        color = PrimaryBackgroundGreen
    ) {
        Scaffold(
            modifier = Modifier.background(color = PrimaryBackgroundGreen)
        ) {
            Box(
                Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(color = PrimaryBackgroundGreen),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Join Kasip.kz",
                        color = Color.White,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Join the reliable Kasip.kz platform and solve any problem online with thousands of freelance services",
                        color = Color.White,
                        fontSize = 21.sp
                    )
                    Spacer(modifier = Modifier.height(60.dp))
                    Card(
                        colors = CardDefaults.elevatedCardColors()
                            .copy(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier
                                    .height(68.dp)
                                    .width(85.dp),
                                painter = painterResource(id = R.drawable.icon),
                                contentDescription = ""
                            )
                            Button(
                                onClick = { navigateTo(registration) },
                                colors = ButtonDefaults.buttonColors()
                                    .copy(containerColor = PrimaryBackgroundGreen)
                            ) {
                                Text(
                                    text = stringResource(R.string.register_with_email),
                                    color = Color.Black
                                )
                            }
                            TextButton(onClick = { navigateTo(login) }) {
                                Text(
                                    text = stringResource(R.string.sign_in),
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewOnboardingScreen() {
    OnboardingScreen { }
}