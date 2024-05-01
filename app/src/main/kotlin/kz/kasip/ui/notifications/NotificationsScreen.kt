package kz.kasip.ui.notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.kasip.chat.ui.chat.Notification
import kz.kasip.designcore.KasipTopAppBar
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.notifications
import java.util.Date

@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = lang[notifications] ?: "",
                    onBack = onBack
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(it),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                val notifications by viewModel.notifications.collectAsState()
                notifications.forEach { (note, isViewed) ->
                    NotificationItem(
                        note = note,
                        isViewed = isViewed
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationItem(
    note: Notification,
    isViewed: Boolean,
) {
    Surface {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 36.dp)
        ) {
            Column(
                modifier = Modifier
                    .width(36.dp)
                    .align(Alignment.Bottom)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = "${note.sentAt.hours}:${note.sentAt.minutes}"
                )
            }
            if (!isViewed) {
                Card(
                    colors = CardDefaults.outlinedCardColors()
                        .copy(containerColor = Color(0xD9D9D9FF), contentColor = Color.Black)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        Note(note = note)
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Note(note = note)
                }
            }
        }
    }
}

@Composable
fun Note(note: Notification) {
    Text(
        text = note.title,
        fontSize = 21.sp,
        fontWeight = FontWeight(700)
    )
    Spacer(modifier = Modifier.height(24.dp))
    Text(
        text = note.body,
        fontSize = 10.sp,
        fontWeight = FontWeight(700)
    )
}

@Preview
@Composable
fun PreviewNotificationItem() {
    NotificationItem(
        note = Notification(
            title = "Title",
            body = "Bodad.fgkjlnsdlfkgjbsdlkfhjbglkshdbfgl;kjasbdg;kjaba;subdjgflkashdbflakshdbflkhasbdflkajbsdlkfhbasldkhfby",
            chatId = "",
            userId = "",
            sentAt = Date(),
        ),
        isViewed = false
    )
}
