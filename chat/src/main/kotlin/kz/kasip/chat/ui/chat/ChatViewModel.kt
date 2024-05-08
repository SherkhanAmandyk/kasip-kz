package kz.kasip.chat.ui.chat

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kz.kasip.data.FCM
import kz.kasip.data.Push
import kz.kasip.data.PushData
import kz.kasip.data.entities.Chat
import kz.kasip.data.entities.Message
import kz.kasip.data.entities.Profile
import kz.kasip.data.entities.User
import kz.kasip.data.mappers.toChat
import kz.kasip.data.mappers.toMessage
import kz.kasip.data.mappers.toProfile
import kz.kasip.data.mappers.toUser
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.message_from_user
import java.util.Date

@HiltViewModel(assistedFactory = ChatViewModelFactory::class)
class ChatViewModel @AssistedInject constructor(
    @Assisted val charId: String,
    val dataStoreRepository: DataStoreRepository,
    private val fcm: FCM,
) : ViewModel() {
    val messagesDoc = "chats/$charId/messages"

    private val chatFlow = MutableStateFlow<Chat?>(null)
    val userFlow = MutableStateFlow<User?>(null)
    val profileFlow = MutableStateFlow<Profile?>(null)
    val amIBlocked = MutableStateFlow(false)
    val didIBlock = MutableStateFlow(false)

    val messageFlow = MutableStateFlow(TextFieldValue(""))
    val messagesFlow = MutableStateFlow<List<Pair<Boolean, Message>>>(emptyList())
    val messagesFinalFlow = combine(chatFlow, messagesFlow) { chat, messages ->
        messages.filter { chat == null || it.second.sentAt.after(chat.deletedTill) }
            .sortedBy { it.second.sentAt }
    }

    init {
        val myUserId = dataStoreRepository.getUserId()
        Firebase.firestore.collection(messagesDoc).orderBy("sentAt")
            .addSnapshotListener { value, _ ->
                messagesFlow.update {
                    value?.documents?.map {
                        it.toMessage()
                    }?.map {
                        (it.authorUserId == myUserId) to it
                    } ?: emptyList()
                }
            }

        Firebase.firestore.document("chats/$charId")
            .addSnapshotListener { value, error ->
                value?.toChat()?.let { chat ->
                    chatFlow.update { chat }
                }
            }

        viewModelScope.launch {
            chatFlow.collect { chatNullable ->
                chatNullable?.let { chat ->
                    Firebase.firestore.collection("users")
                        .document(chat.participantUserIds.first { it != myUserId })
                        .addSnapshotListener { value, _ ->
                            value?.toUser()?.let { user -> userFlow.update { user } }
                        }
                    amIBlocked.update {
                        chat.blockedBy.any { it != dataStoreRepository.getUserId() }
                    }
                    didIBlock.update {
                        chat.blockedBy.contains(dataStoreRepository.getUserId())
                    }
                }
            }
        }
        viewModelScope.launch {
            userFlow.collect {
                it?.let { user ->
                    Firebase.firestore.collection("profiles")
                        .whereEqualTo("userId", user.id)
                        .addSnapshotListener { value, error ->
                            value?.documents?.firstOrNull()?.toProfile()?.let { profile ->
                                profileFlow.update {
                                    profile
                                }
                            }
                        }
                }
            }
        }
    }

    fun send() {
        viewModelScope.launch {
            Firebase.firestore.collection(messagesDoc)
                .add(Message(dataStoreRepository.getUserId() ?: "", messageFlow.value.text, Date()))
                .await()
            sendFCM(to = userFlow.value, message = messageFlow.value.text, chatId = charId)
            messageFlow.update { TextFieldValue("") }
        }
    }


    private fun sendFCM(to: User?, message: String, chatId: String?) {
        viewModelScope.launch {
            runCatching {
                fcm.sendPush(
                    push = Push(
                        to = "edcovFInS1-lXGMslti1sQ:APA91bH48KjN--onXSfXcpzDu79rYdw7tYcUuvQE0aM3wqw-LqhO8M6obVwIi3ZJAMvgXFB7SMffwEo6IIso9-kn3wVBV5Y7-hNfncvkjRaVP566C7jO4WAegV22k3JUdvgQxFFxT9Rc",
                        data = PushData(
                            body = message,
                            title = "Message",
                            chatId = chatId
                        )
                    )
                )
                Firebase.firestore.collection("notifications")
                    .add(
                        Notification(
                            title = lang[message_from_user] ?: "",
                            body = message,
                            chatId = chatId,
                            userId = to?.id,
                            sentAt = Date()
                        )
                    )
            }
        }
    }

    fun toggleBlock(didIBlock: Boolean) {
        Firebase.firestore.collection("chats")
            .document(charId)
            .update(
                "blockedBy",
                if (didIBlock) {
                    FieldValue.arrayRemove(dataStoreRepository.getUserId())
                } else {
                    FieldValue.arrayUnion(dataStoreRepository.getUserId())
                }
            )
    }

    fun deleteChat() {
        Firebase.firestore.collection("chats")
            .document(charId)
            .update("deletedTill", Timestamp(Date()))
    }
}