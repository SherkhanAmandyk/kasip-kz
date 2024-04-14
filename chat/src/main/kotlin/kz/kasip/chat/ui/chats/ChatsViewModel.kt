package kz.kasip.chat.ui.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kz.kasip.data.entities.Chat
import kz.kasip.data.entities.Profile
import kz.kasip.data.entities.User
import kz.kasip.data.mappers.toChat
import kz.kasip.data.mappers.toProfile
import kz.kasip.data.mappers.toUser
import kz.kasip.data.repository.DataStoreRepository
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    private val usersFlow = MutableStateFlow<List<User>>(emptyList())
    private val profilesFlow = MutableStateFlow<Map<String, Profile>>(emptyMap())

    val chatsToOpenFlow = combine(
        usersFlow, profilesFlow
    ) { users, profiles ->
        users.map {
            ChatToOpen(
                it,
                profiles[it.id]
            )
        }
    }

    val chatToGo = MutableStateFlow<Chat?>(null)

    init {
        Firebase.firestore.collection("users")
            .addSnapshotListener { value, error ->
                value?.documents?.map {
                    it.toUser()
                }?.filter {
                    it.id != dataStoreRepository.getUserId()
                }?.let { newUsers ->
                    usersFlow.update {
                        newUsers
                    }
                }
            }
        Firebase.firestore.collection("profiles")
            .addSnapshotListener { value, _ ->
                value?.documents?.map {
                    it.toProfile()
                }?.filter {
                    it.userId != dataStoreRepository.getUserId()
                }?.let { newUsers ->
                    profilesFlow.update {
                        newUsers.associateBy { it.userId }
                    }
                }
            }
    }

    fun createOrGetChat(id: String) {
        val me = dataStoreRepository.getUserId() ?: ""
        viewModelScope.launch(Dispatchers.IO) {
            chatToGo.update {
                Firebase.firestore.collection("chats")
                    .get()
                    .await()
                    .documents.map {
                        it.toChat()
                    }.firstOrNull {
                        it.participantUserIds.contains(id) &&
                                it.participantUserIds.contains(dataStoreRepository.getUserId())
                    } ?: createChat(id, me)

            }
        }
    }

    private suspend fun createChat(id: String, me: String): Chat {
        val chat = Chat(
            id = "",
            participantUserIds = listOf(id, me),
            blockedBy = emptyList(),
            deletedTill = Date(0)
        )
        return Firebase.firestore.collection("chats")
            .add(chat)
            .await()
            .let {
                it.update("id", it.id).await()
                chat.copy(id = it.id)
            }
    }

    fun invalidate() {
        chatToGo.update { null }
    }
}

data class ChatToOpen(
    val user: User,
    val profile: Profile?,
)