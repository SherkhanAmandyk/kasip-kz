package kz.kasip.chat.ui.blocklist

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import kz.kasip.data.entities.Chat
import kz.kasip.data.entities.User
import kz.kasip.data.mappers.toChat
import kz.kasip.data.mappers.toUser
import kz.kasip.data.repository.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class BlockListViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    val myUserId = dataStoreRepository.getUserId() ?: ""
    val chatsFlow = MutableStateFlow<List<Chat>>(emptyList())
    val usersFlow: Flow<Map<String, User>> = chatsFlow.map {
        val userIds = it.flatMap { it.participantUserIds }.filter { it != myUserId }
        Firebase.firestore.collection("users")
            .get()
            .await()
            .documents.map(DocumentSnapshot::toUser)
            .filter { userIds.contains(it.id) }
            .associateBy(User::id)
    }
    val blockedChats: Flow<List<Pair<User?, Chat>>> =
        combine(chatsFlow, usersFlow) { chats, users ->
            chats.map {
                users[it.participantUserIds.first { it != myUserId }] to it
            }
        }

    init {
        Firebase.firestore.collection("chats")
            .addSnapshotListener { value, error ->
                value?.documents?.map {
                    it.toChat()
                }?.filter {
                    it.participantUserIds.contains(myUserId) && it.blockedBy.contains(myUserId)
                }?.let { chats ->
                    chatsFlow.update {
                        chats
                    }
                }
            }
    }

    fun unblock(it: Pair<User?, Chat>) {
        Firebase.firestore.document("chats/${it.second.id}")
            .update("blockedBy", FieldValue.arrayRemove(dataStoreRepository.getUserId()))
    }
}