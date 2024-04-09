package kz.kasip.designcore

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatItem(
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
            Image(
                painter = painterResource(
                    id = R.drawable.icon_profile_pic
                ),
                contentDescription = ""
            )
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
    ChatItem() {}
}