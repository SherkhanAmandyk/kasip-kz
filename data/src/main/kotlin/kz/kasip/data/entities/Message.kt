package kz.kasip.data.entities

import java.util.Date

data class Message(
    val authorUserId: String,
    val message: String,
    val sentAt: Date,
)
