package kz.kasip.chat.ui.chat

import dagger.assisted.AssistedFactory

@AssistedFactory
interface ChatViewModelFactory {
    fun create(chatId: String): ChatViewModel
}