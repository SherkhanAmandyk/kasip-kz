package kz.kasip.chat.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.update
import kz.kasip.designcore.KasipTopAppBar
import kz.kasip.designcore.theme.DialogBackground
import kz.kasip.designcore.theme.PrimaryBackgroundGreen

@Composable
fun ChatScreen(
    chatId: String,
    viewModel: ChatViewModel = hiltViewModel<ChatViewModel, ChatViewModelFactory> {
        it.create(chatId)
    },
    onBack: () -> Unit,
) {
    Surface {
        Scaffold(
            topBar = {
                val user by viewModel.userFlow.collectAsState()
                val profile by viewModel.profileFlow.collectAsState()
                KasipTopAppBar(
                    title = profile?.name ?: user?.email ?: "Chats",
                    onBack = onBack
                )
            },
            bottomBar = {
                val text by viewModel.messageFlow.collectAsState()
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = text,
                        onValueChange = { text ->
                            viewModel.messageFlow.update { text }
                        },
                        trailingIcon = {
                            IconButton(onClick = { viewModel.send() }) {
                                Icon(
                                    painter = painterResource(id = kz.kasip.designcore.R.drawable.icon_forward),
                                    contentDescription = ""
                                )
                            }
                        })
                }
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    val scrollState = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val chats by viewModel.messagesFlow.collectAsState(emptyList())
                        chats.forEach { (mine, message) ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = if (mine) {
                                    Arrangement.End
                                } else {
                                    Arrangement.Start
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .background(
                                            color = if (mine) {
                                                DialogBackground
                                            } else {
                                                PrimaryBackgroundGreen
                                            },
                                            shape = RoundedCornerShape(25.dp)
                                        )
                                        .padding(vertical = 12.dp, horizontal = 24.dp)
                                ) {
                                    Text(text = message.message)
                                    Text(
                                        modifier = Modifier.align(Alignment.End),
                                        text = "${message.sentAt.hours}:${message.sentAt.minutes}"
                                    )
                                }
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
fun PreviewChatsScreen() {
    ChatScreen("") {

    }
}