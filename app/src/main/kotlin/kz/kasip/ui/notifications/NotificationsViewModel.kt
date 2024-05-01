package kz.kasip.ui.notifications

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kz.kasip.chat.ui.chat.Notification
import kz.kasip.data.repository.DataStoreRepository
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val notifications = MutableStateFlow<List<Pair<Notification, Boolean>>>(emptyList())

    init {
        Firebase.firestore.collection("notifications")
            .whereEqualTo("userId", dataStoreRepository.getUserId())
            .addSnapshotListener { value, error ->
                value?.documents?.map { it.toNotification() to dataStoreRepository.isNotified(it.id) }
                    ?.let { notifs ->
                        notifications.update { notifs }
                    }
            }
    }
}

private fun DocumentSnapshot.toNotification(): Notification {
    return Notification(
        title = getString("title") ?: "",
        body = getString("body") ?: "",
        chatId = getString("chatId"),
        userId = getString("userId"),
        sentAt = getDate("sentAt") ?: Date()
    )
}
