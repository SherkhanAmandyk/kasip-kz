package kz.kasip

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import kz.kasip.data.repository.DataStoreRepository

class KasipFirebaseCloudMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val dataStoreRepository = DataStoreRepository(
            getSharedPreferences(
                "kasip",
                Context.MODE_PRIVATE
            )
        )
        dataStoreRepository.getUserId()?.let {
            Firebase.firestore.document("users/$it").update("fcmToken", token)
        }
    }
}