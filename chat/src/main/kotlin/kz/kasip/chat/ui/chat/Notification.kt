package kz.kasip.chat.ui.chat

import com.google.firebase.Timestamp
import java.util.Date

data class Notification(
    val title: String,
    val body: String,
    val chatId: String?,
    val userId: String?,
    val sentAt: Date,
)
