package kz.kasip.deigncore

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun MainTopAppBar() {
    val profileInfo = ConstrainedLayoutReference("profileInfo")
    val payment = ConstrainedLayoutReference("payment")
    val status = ConstrainedLayoutReference("status")
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.Green,
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
                Text(text = "Name")
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(45.dp)
            )
            .constrainAs(payment) {
                top.linkTo(profileInfo.bottom)
                start.linkTo(parent.start)
            }) {
            Spacer(modifier = Modifier.height(44.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Balance:")
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Top up")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Transaction history")
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Withdraw")
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(28.dp)
                .fillMaxWidth()
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(33.dp)
                )
                .border(0.5.dp, shape = RoundedCornerShape(33.dp), color = Color.Black)
                .constrainAs(status) {
                    top.linkTo(profileInfo.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(payment.top)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(start = 24.dp),
                text = "Status Mode"
            )
            var toggleChecked by remember { mutableStateOf(true) }
            Switch(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .padding(end = 10.dp),
                checked = toggleChecked,
                onCheckedChange = { toggleChecked = it })
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
                    MainTopAppBar()
                }
            }
        ) {
            Box(modifier = Modifier.padding(it))
        }
    }
}