package kz.kasip.chat.ui.chat

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kz.kasip.data.entities.Chat
import kz.kasip.data.entities.Message
import kz.kasip.data.entities.Profile
import kz.kasip.data.entities.User
import kz.kasip.data.mappers.toChat
import kz.kasip.data.mappers.toMessage
import kz.kasip.data.mappers.toProfile
import kz.kasip.data.mappers.toUser
import kz.kasip.data.repository.DataStoreRepository
import java.util.Date

@HiltViewModel(assistedFactory = ChatViewModelFactory::class)
class ChatViewModel @AssistedInject constructor(
    @Assisted val charId: String,
    val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val messagesDoc = "chats/$charId/messages"

    private val chatFlow = MutableStateFlow<Chat?>(null)
    val userFlow = MutableStateFlow<User?>(null)
    val profileFlow = MutableStateFlow<Profile?>(null)

    val messageFlow = MutableStateFlow(TextFieldValue(""))
    val messagesFlow = MutableStateFlow<List<Pair<Boolean, Message>>>(emptyList())

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

        viewModelScope.launch {
            chatFlow.update {
                Firebase.firestore.collection("chats")
                    .document(charId)
                    .get()
                    .await()
                    .toChat()
            }
            chatFlow.collect {
                it?.let { chat ->
                    userFlow.update {
                        Firebase.firestore.collection("users")
                            .document(chat.participantUserIds.first { it != myUserId })
                            .get()
                            .await()
                            .toUser()
                    }
                }
            }
            userFlow.collect {
                it?.let { user ->
                    profileFlow.update {
                        Firebase.firestore.collection("profiles")
                            .document(user.id)
                            .get()
                            .await()
                            .toProfile()
                    }
                }
            }
        }
    }

    fun send() {
        viewModelScope.launch {
            Firebase.firestore.collection(messagesDoc)
                .add(Message(dataStoreRepository.getUserId() ?: "", messageFlow.value.text, Date()))
            messageFlow.update { TextFieldValue("") }
        }
    }
}