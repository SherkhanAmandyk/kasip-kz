package kz.kasip.data.entities

import java.util.Date

data class Chat(
    val id: String,
    val participantUserIds: List<String>,
    val blockedBy: List<String>,
    val deletedTill: Date
)