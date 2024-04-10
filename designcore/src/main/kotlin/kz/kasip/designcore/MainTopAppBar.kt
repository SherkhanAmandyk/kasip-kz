package kz.kasip.designcore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import kz.kasip.designcore.theme.PrimaryBackgroundGreen

@Composable
fun MainTopAppBar(name: String) {
    val profileInfo = ConstrainedLayoutReference("profileInfo")
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(
                color = PrimaryBackgroundGreen,
                shape = RoundedCornerShape(
                    bottomStart = 45.dp,
                    bottomEnd = 45.dp
                )
            )
            .constrainAs(profileInfo) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            Column(
                Modifier
                    .padding(top = 28.dp, bottom = 32.dp)
                    .padding(horizontal = 28.dp)
                    .align(Alignment.Center)
            ) {
                Spacer(modifier = Modifier.height(72.dp))
                Icon(
                    painter = painterResource(id = R.drawable.icon_profile_pic),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = name)
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainTopAppBar() {
    Surface {
        Scaffold(
            topBar = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    MainTopAppBar("Name")
                }
            }
        ) {
            Box(modifier = Modifier.padding(it))
        }
    }
}