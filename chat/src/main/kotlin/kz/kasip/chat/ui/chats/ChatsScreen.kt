package kz.kasip.chat.ui.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.kasip.designcore.ChatItem
import kz.kasip.designcore.KasipTopAppBar

@Composable
fun ChatsScreen(
    viewModel: ChatsViewModel = hiltViewModel(),
    onGoToChat: (String) -> Unit,
    onBack: () -> Unit,
) {
    val chat by viewModel.chatToGo.collectAsState()
    LaunchedEffect(chat) {
        chat?.id?.let {
            onGoToChat(it)
            viewModel.invalidate()
        }
    }
    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(title = "Chats", onBack = onBack)
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                Column {
                    val chats by viewModel.chatsToOpenFlow.collectAsState(emptyList())
                    chats.forEach {
                        Box(modifier = Modifier.padding(vertical = 16.dp)) {
                            ChatItem(
                                name = it.profile?.name?.takeIf { it.isNotEmpty() } ?: it.user.email
                            ) {
                                viewModel.createOrGetChat(it.user.id)
                            }
                        }
                        Row {
                            Spacer(modifier = Modifier.width(72.dp))
                            Box(
                                modifier = Modifier
                                    .background(color = Color.Black)
                                    .fillMaxWidth()
                                    .height(1.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewChatsScreen() {
    ChatsScreen(onGoToChat = {}) {

    }
}