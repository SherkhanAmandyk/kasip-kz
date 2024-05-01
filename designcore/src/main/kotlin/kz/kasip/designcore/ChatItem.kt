package kz.kasip.designcore

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ChatItem(
    avatar: Uri?,
    name: String = "Max Holloway",
    goToChat: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(40.dp))
            if (avatar != null) {
                Image(
                    modifier = Modifier
                        .height(75.dp)
                        .width(75.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Black, CircleShape),
                    painter = rememberAsyncImagePainter(
                        avatar,
                        contentScale = ContentScale.Crop,

                    ),
                    contentDescription = ""
                )
            } else {
                Image(
                    painter = painterResource(
                        id = R.drawable.icon_profile_pic
                    ),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = name, fontWeight = FontWeight(700), fontSize = 18.sp)
        }
        IconButton(onClick = { goToChat() }) {
            Icon(painter = painterResource(id = R.drawable.chat), contentDescription = "")
        }
    }
}

@Preview
@Composable
fun PreviewChatItem() {
    ChatItem(goToChat = {}, avatar = null)
}