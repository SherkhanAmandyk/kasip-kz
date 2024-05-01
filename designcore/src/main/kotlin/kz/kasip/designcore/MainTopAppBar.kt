package kz.kasip.designcore

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.theme.PrimaryBackgroundGreen

@Composable
fun MainTopAppBar(
    name: String,
    profession: String,
    avatar: Uri?,
    onChangeImage: () -> Unit,
) {
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
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Spacer(modifier = Modifier.height(72.dp))
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    IconButton(
                        modifier = Modifier
                            .height(75.dp)
                            .width(75.dp),
                        onClick = {
                            onChangeImage()
                        }) {
                        if (avatar != null) {
                            Image(
                                modifier = Modifier
                                    .height(75.dp)
                                    .width(75.dp)
                                    .border(1.dp, Color.Black, CircleShape),
                                painter = rememberAsyncImagePainter(
                                    avatar,
                                    contentScale = ContentScale.FillBounds
                                ),
                                contentDescription = ""
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.icon_profile_pic),
                                contentDescription = null
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = name)
                Text(text = profession)
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
                    MainTopAppBar("Aslan Mergenov", lang[ui_designer]?:"" ?: "", null) {}
                }
            }
        ) {
            Box(modifier = Modifier.padding(it))
        }
    }
}