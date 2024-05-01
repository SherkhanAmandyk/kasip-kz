package kz.kasip

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kz.kasip.RootActivity.Companion.main
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.onboarding.navigation.onboarding
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    var start: String = if (dataStoreRepository.getUserId() == null) {
        onboarding
    } else {
        main
    }
    val chatIdFlow = MutableStateFlow<String?>(null)


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val docs =
                Firebase.firestore.collection("rubrics").get().await().documents
            println(docs)
        }
    }

    fun goToChat(chatId: String) {
        chatIdFlow.update { chatId }
    }

    fun savePhoto(uri: Uri) {
        val riversRef =
            Firebase.storage.reference.child("avatar/${dataStoreRepository.getUserId()}")
        val uploadTask = riversRef.putFile(uri)

        uploadTask.addOnFailureListener {
        }.addOnSuccessListener { taskSnapshot ->
        }
    }
}

