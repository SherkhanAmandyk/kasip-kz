package kz.kasip.deigncore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun MainTopAppBar() {
    val profileInfo = ConstrainedLayoutReference("profileInfo")
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier
            .background(color = Color.Green, shape = RectangleShape)
            .constrainAs(profileInfo) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            Column(
                Modifier
                    .padding(top = 28.dp, bottom = 32.dp)
                    .padding(horizontal = 28.dp)
            ) {
                Text(text = "Name")
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainTopAppBar() {
    MainTopAppBar()
}