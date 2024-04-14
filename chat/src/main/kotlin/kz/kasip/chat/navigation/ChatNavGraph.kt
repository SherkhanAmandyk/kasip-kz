package kz.kasip.chat.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kz.kasip.chat.ui.blocklist.BlockListScreen
import kz.kasip.chat.ui.chat.ChatScreen
import kz.kasip.chat.ui.chats.ChatsScreen

const val chatsScreen = "chatsScreen"
const val blockListScreen = "blockListScreen"
const val chatScreen = "chatScreen/{chatId}"

fun NavGraphBuilder.chatGraph(
    onGoToChat: (String) -> Unit,
    onBack: () -> Unit,
) {
    composable(chatsScreen) {
        ChatsScreen(
            onGoToChat = onGoToChat,
            onBack = onBack
        )
    }
    composable(
        chatScreen,
        arguments = listOf(navArgument("chatId") { type = NavType.StringType })
    ) {
        ChatScreen(
            chatId = it.arguments?.getString("chatId", "") ?: "",
            onBack = onBack
        )
    }
    composable(blockListScreen) {
        BlockListScreen(onBack = onBack)
    }
}