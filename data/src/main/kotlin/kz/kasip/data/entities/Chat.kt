package kz.kasip.data.entities

data class Chat(
    val id: String,
    val participantUserIds: List<String>,
)