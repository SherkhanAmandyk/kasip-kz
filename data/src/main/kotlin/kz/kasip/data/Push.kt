package kz.kasip.data

data class Push(
    val to: String?,
    val data: PushData,
)

data class PushData(
    val body: String,
    val title: String,
    val chatId: String?
)
